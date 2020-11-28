	#include <stdio.h> 
	#include <sys/socket.h>
	#include <netinet/tcp.h>
	#include <time.h>
	#include <arpa/inet.h> 
	#include <stdlib.h> 
	#include <string.h> 
	#include <unistd.h> 
	#define SERVER_PORT 53
	#define BUF_SIZE 1024 
	
	unsigned short length = 0;//发送数据长度 
	
	//全局枚举变量，返回十六进制值。 
	enum tag_type {
	  standard_query_NRD = 0x0000,    // 迭代 查询 
	  standard_res_NAA_NRA = 0x8000,  // 非权威 迭代 
	  standard_res_AA_NRA = 0x8400,   // 权威  迭代 
	  name_wrong_res = 0x8403,        // 域名错误（查不到该域名） 
	  format_wrong_res = 0x8401       //格式错误 
	} tag;

	//RR查询类型 
	enum RR_type {
	  A = 0x0001,
      CNAME = 0x0005,
      MX = 0x000F
	};
	
	struct dnsHeader{
		unsigned short id;
		unsigned short tag;
		unsigned short queryNum;
		unsigned short answerNum;
		unsigned short authorNum;
		unsigned short addNum;
	}; 
	
	struct dnsQuery{
		char* qName;
  		unsigned short qType;
  		unsigned short qClass;
	};
	
	struct dnsRR_CNAME{
		char* dname;
  		unsigned short type;
  		unsigned short _class;
  		unsigned int ttl;
  		unsigned short rDataLen;
  		char* rData;
	};
	
	struct dnsRR_A{
		char* dname;
  		unsigned short type;
  		unsigned short _class;
  		unsigned int ttl;
  		unsigned short rDataLen;
  		unsigned int rData;
	};
	
	struct dnsRR_MX{
		char* dname;
  		unsigned short type;
  		unsigned short _class;
  		unsigned int ttl;
  		unsigned short rDataLen;
  		char* rData;
	};
	
	struct packet{
		struct dnsHeader* header;
		struct dnsQuery* querySection;
		
		struct dnsRR_A* A_answerSection;
		struct dnsRR_A* A_authoritySection;
		struct dnsRR_A* A_additionalSection;
		
		struct dnsRR_MX* MX_answerSection;
		struct dnsRR_MX* MX_authoritySection;
		struct dnsRR_MX* MX_additionalSection;
		
		struct dnsRR_CNAME* C_answerSection;
		struct dnsRR_CNAME* C_authoritySection;
		struct dnsRR_CNAME* C_additionalSection;
	};
	
	void initPacket(struct packet* Packet, int flag);
	void printQpacket(struct packet packet);
	void printApacket(struct packet packet);
	
	void put16bits(char** buffer, unsigned short value);
	unsigned short get16bits(char** buffer);
	void put32bits(char** buffer, unsigned int value);
	unsigned int get32bits(char** buffer);
	unsigned short getType(char *type);
	char* cutDomainName(char* domain_name, int times);
	
	void encode_packet(struct packet* packet, char** buffer);
	void encode_header(struct dnsHeader* hd, char** buffer); 
	void encode_domain_name(char** buffer, char* domain);
	void int_encode_resource_records(struct packet* packet, char** buffer, int flag);
	void char_encode_resource_records(struct packet* packet, char** buffer, int flag);
	
	void decode_packet(struct packet* packet, char** buffer);
	void decode_header(struct dnsHeader* hd, char** buffer);
	char* decode_domain_name(char** buffer);
	
	int getRR (struct dnsRR_A* p, char* fpath,char* qName, unsigned short qType);
	int existRR(char* qName, unsigned short qType, struct dnsRR_A* rr);
	void parse_A_rData(struct dnsRR_A* p, char* buffer); 
	void parse_CNAME_rData(struct dnsRR_A* p, char* buffer); 
	void parse_MX_rData(struct dnsRR_A* p, char* buffer, unsigned short prfrc);
	
	int main(int argc, char *argv[]){
		
		char* serverIP = "127.0.0.7";
		
  		int sockfd; 
		struct sockaddr_in my_addr; 
		struct sockaddr_in remote_addr;
		
		//创建端口 
		if ((sockfd = socket(PF_INET, SOCK_DGRAM, 0)) == -1) { 
		  	printf("Something wrong with socket creation\n"); 
		    exit(1); 
		}
		//本地服务器设置 
		memset(&my_addr, 0, sizeof(my_addr));
		my_addr.sin_family = AF_INET; 
		my_addr.sin_port = htons(SERVER_PORT); 
		my_addr.sin_addr.s_addr = inet_addr(serverIP);
		//IP绑定端口 
		if ((bind(sockfd, (struct sockaddr *) &my_addr, sizeof(my_addr))) == -1) {
		    printf("Something wrong with socket binding\n");
		    exit(1); 
		}
		
		while(1){
			char* rec_buf = (char*)malloc(sizeof(char) * BUF_SIZE);
    		memset(rec_buf, 0, BUF_SIZE);
    		char* h_rec_buf = rec_buf;//指针指向最初位置
    		
    		//接受来自本地服务器的报文 
    		unsigned int AddrLen = sizeof(remote_addr);
    		if ((recvfrom(sockfd, rec_buf, BUF_SIZE,0,(struct sockaddr *) &remote_addr, &AddrLen) < 0)){
      			printf("Something wrong with socket receving\n");
    		}
    		printf("Received a packet from Localserver : %s\n", inet_ntoa(remote_addr.sin_addr));
    		
    		struct packet qPacket;
    		initPacket(&qPacket, 0);
    		decode_packet(&qPacket, &rec_buf);
    		printQpacket(qPacket);
    		//读取顶级域名 
		    char* temp_domain_name = qPacket.querySection->qName;
		    char* domain_name = cutDomainName(temp_domain_name, 2);
		    
		    struct dnsRR_A* aRR = (struct dnsRR_A*)malloc(sizeof(struct dnsRR_A));
    		memset(aRR, 0, sizeof(struct dnsRR_A));
    		
    		struct dnsQuery* query = qPacket.querySection;
    		int flag = getRR(aRR, "./resource_records.txt",domain_name,query->qType);
    		struct packet aPacket;
    		initPacket(&aPacket, 1);
    		
    		if(flag == 1){
    			
    			aPacket.header->id = 0x0001;
			    aPacket.header->tag = standard_res_AA_NRA;
			    aPacket.header->queryNum = 0x0001;
			    aPacket.header->answerNum = 0x0000;
			    aPacket.header->addNum = 0x0000;
			    aPacket.header->authorNum = 0x0001;
			    aPacket.querySection = query;
			    aPacket.A_answerSection = NULL;
			    aPacket.A_authoritySection = aRR;//告诉顶级域名的IP地址
				aPacket.A_authoritySection->type = 0x0001; 
			    aPacket.A_additionalSection = NULL;
			    
			    aPacket.C_answerSection = NULL;
			    aPacket.C_authoritySection = NULL;
			    aPacket.C_additionalSection = NULL;
			    aPacket.MX_answerSection = NULL;
			    aPacket.MX_authoritySection = NULL;
			    aPacket.MX_additionalSection = NULL;
			}
			else{
				aPacket.header = qPacket.header;
			    aPacket.querySection = qPacket.querySection;
			    aPacket.header->tag = name_wrong_res;
			    
			    aPacket.A_answerSection = NULL;
			    aPacket.A_authoritySection = NULL;
			    aPacket.A_additionalSection = NULL;
			    aPacket.C_answerSection = NULL;
			    aPacket.C_authoritySection = NULL;
			    aPacket.C_additionalSection = NULL;
			    aPacket.MX_answerSection = NULL;
			    aPacket.MX_authoritySection = NULL;
			    aPacket.MX_additionalSection = NULL;
			}
			
			char* temp_send_buf = (char*)malloc(sizeof(char) * BUF_SIZE);
		    memset(temp_send_buf, '\0', BUF_SIZE);
		    char* send_buf = temp_send_buf;
		    aPacket.A_authoritySection->dname = query->qName;//域名 
		    encode_packet(&aPacket, &temp_send_buf);
		    
		    if ((sendto(sockfd, send_buf, length, 0, (struct sockaddr *) &remote_addr, sizeof(remote_addr))) == -1) {
      			printf("Something wrong with socket sending packet\n");
      			exit(1); 
    		}
    		printf("Send a packet to Localserver : %s\n", inet_ntoa(remote_addr.sin_addr));
    		
		}
		return 0;
	}

	void initPacket(struct packet* Packet, int flag){
		
		Packet->header = (struct dnsHeader*)malloc(sizeof(struct dnsHeader));
    	memset(Packet->header, 0, sizeof(struct dnsHeader));
    	
    	Packet->querySection = (struct dnsQuery*)malloc(sizeof(struct dnsQuery));
    	memset(Packet->querySection, 0, sizeof(struct dnsQuery));
    	
    	Packet->querySection->qName = (char *)malloc(sizeof(char) * BUF_SIZE);
    	memset(Packet->querySection->qName, '\0', BUF_SIZE);
    	
    	if(flag == 0){
    		Packet->A_answerSection = NULL;
    		Packet->A_authoritySection = NULL;
    		Packet->A_additionalSection = NULL;
    		
    		Packet->MX_answerSection = NULL;
    		Packet->MX_authoritySection = NULL;
    		Packet->MX_additionalSection = NULL;
    		
    		Packet->C_answerSection = NULL;
    		Packet->C_authoritySection = NULL;
    		Packet->C_additionalSection = NULL;
		}
		else{
			//A
			Packet->A_answerSection = (struct dnsRR_A*)malloc(sizeof(struct dnsRR_A));
    		memset(Packet->A_answerSection, 0, sizeof(struct dnsRR_A));
    		Packet->A_answerSection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->A_answerSection->dname, '\0', BUF_SIZE);
    		//CNAME
    		Packet->C_answerSection = (struct dnsRR_CNAME*)malloc(sizeof(struct dnsRR_CNAME));
    		memset(Packet->C_answerSection, 0, sizeof(struct dnsRR_CNAME));
    		Packet->C_answerSection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->C_answerSection->dname, '\0', BUF_SIZE);
    		Packet->C_answerSection->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->C_answerSection->rData, '\0', BUF_SIZE);
    		//MX
    		Packet->MX_answerSection = (struct dnsRR_MX*)malloc(sizeof(struct dnsRR_MX));
    		memset(Packet->MX_answerSection, 0, sizeof(struct dnsRR_MX));
    		Packet->MX_answerSection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->MX_answerSection->dname, '\0', BUF_SIZE);
    		Packet->MX_answerSection->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->MX_answerSection->rData, '\0', BUF_SIZE);
    		
    		//A
			Packet->A_authoritySection = (struct dnsRR_A*)malloc(sizeof(struct dnsRR_A));
    		memset(Packet->A_authoritySection, 0, sizeof(struct dnsRR_A));
    		Packet->A_authoritySection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->A_authoritySection->dname, '\0', BUF_SIZE);
    		//CNAME
    		Packet->C_authoritySection = (struct dnsRR_CNAME*)malloc(sizeof(struct dnsRR_CNAME));
    		memset(Packet->C_authoritySection, 0, sizeof(struct dnsRR_CNAME));
    		Packet->C_authoritySection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->C_authoritySection->dname, '\0', BUF_SIZE);
    		Packet->C_authoritySection->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->C_authoritySection->rData, '\0', BUF_SIZE);
    		//MX
    		Packet->MX_authoritySection = (struct dnsRR_MX*)malloc(sizeof(struct dnsRR_MX));
    		memset(Packet->MX_authoritySection, 0, sizeof(struct dnsRR_MX));
    		Packet->MX_authoritySection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->MX_authoritySection->dname, '\0', BUF_SIZE);
    		Packet->MX_authoritySection->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->MX_authoritySection->rData, '\0', BUF_SIZE);
    		
    		//A
			Packet->A_additionalSection = (struct dnsRR_A*)malloc(sizeof(struct dnsRR_A));
    		memset(Packet->A_additionalSection, 0, sizeof(struct dnsRR_A));
    		Packet->A_additionalSection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->A_additionalSection->dname, '\0', BUF_SIZE);
    		//CNAME
    		Packet->C_additionalSection = (struct dnsRR_CNAME*)malloc(sizeof(struct dnsRR_CNAME));
    		memset(Packet->C_additionalSection, 0, sizeof(struct dnsRR_CNAME));
    		Packet->C_additionalSection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->C_additionalSection->dname, '\0', BUF_SIZE);
    		Packet->C_additionalSection->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->C_additionalSection->rData, '\0', BUF_SIZE);
    		//MX
    		Packet->MX_additionalSection = (struct dnsRR_MX*)malloc(sizeof(struct dnsRR_MX));
    		memset(Packet->MX_additionalSection, 0, sizeof(struct dnsRR_MX));
    		Packet->MX_additionalSection->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->MX_additionalSection->dname, '\0', BUF_SIZE);
    		Packet->MX_additionalSection->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
    		memset(Packet->MX_additionalSection->rData, '\0', BUF_SIZE);
		}
	}
	void put16bits(char** buffer, unsigned short value) {
		
	  value = htons(value);//转换为网络字节序 
	  memcpy(*buffer, &value, 2);
	  *buffer += 2;
	  length += 2;
	}
	unsigned short get16bits(char** buffer) {
		
	  unsigned short value;
	  memcpy(&value, *buffer, 2);
	  *buffer += 2;
	  return ntohs(value);//转换为主机字节序 
	}
	void put32bits(char** buffer, unsigned int value) {
		
	  value = htonl(value);
	  memcpy(*buffer, &value, 4);//一个int占四个字节 
	  *buffer += 4;
	  length += 4;
	}
	unsigned int get32bits(char** buffer) {
		
	  unsigned int value;
	  memcpy(&value, *buffer, 4);
	  *buffer += 4;
	  return ntohl(value);
	}
	//返回查询类型 
	unsigned short getType(char *type) {
		
		enum RR_type type_code;
		
		if (!strcmp(type, "A")) {
			type_code = A;
		    return type_code;//0x0001 
		} 
		else if (!strcmp(type, "MX")) {
		    type_code = MX;
		    return type_code;//0x0005
		} 
		else if (!strcmp(type, "CNAME")) {
		    type_code = CNAME;
		    return type_code;//0x000F
		} 
		else {
		    printf("No such query type, [A | MX | CNAME] is considered\n");
		    exit(0);
		}
	} 
	// 读取顶级域名 
	char* cutDomainName(char* domain_name, int times) {
		
	  unsigned short charLength = strlen(domain_name);
	  domain_name += charLength - 1;//移动指针到最高位 
	  int j = 0;
	  int i = 0;
	  while (i != times) {
	    j++;
	    domain_name--;
	    if (*domain_name == '.') {
	      i++;
	    }
	  }
	  domain_name++;//移动指针到顶级域的最初位置 
	  char* server_name = (char*)malloc(sizeof(char) * BUF_SIZE);
	  memset(server_name, 0, BUF_SIZE);
	  memcpy(server_name, domain_name, j);
	  return server_name;//返回顶级域名 
	}
	void encode_packet(struct packet* packet, char** buffer) {
	  
	  encode_header(packet->header, buffer);
	  encode_domain_name(buffer, packet->querySection->qName);
	  put16bits(buffer, packet->querySection->qType);
	  put16bits(buffer, packet->querySection->qClass);
	  
	  if(packet->header->answerNum != 0){
	    if(packet->querySection->qType == 1){
	    	int_encode_resource_records(packet, buffer, 0);
		}
		else if(packet->querySection->qType == 5){
			char_encode_resource_records(packet, buffer, 0);
		}
		else{
			char_encode_resource_records(packet, buffer, 0);
		}
	  }
	  else {
	    packet->A_answerSection = NULL;
	    packet->C_answerSection = NULL;
	    packet->MX_answerSection = NULL;
	  }
	  if(packet->header->authorNum != 0){
		if(packet->querySection->qType == 1){
	    	int_encode_resource_records(packet, buffer, 1);
		}
		else if(packet->querySection->qType == 5){
			int_encode_resource_records(packet, buffer, 1);
		}
		else{
			int_encode_resource_records(packet, buffer, 1);
		}
	  }
	  else {
	    packet->A_authoritySection = NULL;
	    packet->C_authoritySection = NULL;
	    packet->MX_authoritySection = NULL;
	  }
	  if(packet->header->addNum != 0){
		if(packet->querySection->qType == 1){
	    	int_encode_resource_records(packet, buffer, 2);
		}
		else if(packet->querySection->qType == 5){
			char_encode_resource_records(packet, buffer, 2);
		}
		else{
			int_encode_resource_records(packet, buffer, 2);
		}
	  }
	  else {
	    packet->A_additionalSection = NULL;
	    packet->C_additionalSection = NULL;
	    packet->MX_additionalSection = NULL;
	  }
	}
	//头文件编码 
	void encode_header(struct dnsHeader* hd, char** buffer) {
		
	  put16bits(buffer, hd->id);
	  put16bits(buffer, hd->tag);
	  put16bits(buffer, hd->queryNum);
	  put16bits(buffer, hd->answerNum);
	  put16bits(buffer, hd->authorNum);
	  put16bits(buffer, hd->addNum);
	}
	//域名编码 
	void encode_domain_name(char** buffer, char* domain) {
		
	  int j = -1;
	  do {
	    j++;
	    if (domain[j] == '.' || domain[j] == '\0') {
	      **buffer = j;//存放每个域的长度 
	      *buffer += 1;
	      length += 1;
	      memcpy(*buffer, domain, j);
	      *buffer += j;
	      length += j;
	      domain = domain + j + 1;//读取.后的内容 
	      j = -1;
	    }
	  } while (domain[j] != '\0');
	  **buffer = 0;//结尾为0 
	  *buffer += 1;
	  length += 1;
	}
	//RR编码 
	void int_encode_resource_records(struct packet* packet, char** buffer,int flag) {
	    
	    if(flag == 0){
	    	encode_domain_name(buffer, packet->A_answerSection->dname);
	    	put16bits(buffer, packet->A_answerSection->type);
	    	put16bits(buffer, packet->A_answerSection->_class);
	    	put32bits(buffer, packet->A_answerSection->ttl);
	    	put16bits(buffer, packet->A_answerSection->rDataLen);
	    	memcpy(*buffer, &packet->A_answerSection->rData, 4);//一个int占四个字节 
	  		*buffer += 4;
	  		length += 4;
		}
		else if(flag == 1){
			
			encode_domain_name(buffer, packet->A_authoritySection->dname);
	    	put16bits(buffer, packet->A_authoritySection->type);
	    	put16bits(buffer, packet->A_authoritySection->_class);
	    	put32bits(buffer, packet->A_authoritySection->ttl);
	    	put16bits(buffer, packet->A_authoritySection->rDataLen);
	    	memcpy(*buffer, &packet->A_authoritySection->rData, 4);//一个int占四个字节 
	  		*buffer += 4;
	  		length += 4;
		}
		else{
			if(packet->querySection->qType == 15){
				encode_domain_name(buffer, packet->MX_additionalSection->dname);
	    		put16bits(buffer, packet->MX_additionalSection->type);
	    		put16bits(buffer, packet->MX_additionalSection->_class);
	    		put32bits(buffer, packet->MX_additionalSection->ttl);
	    		put16bits(buffer, packet->MX_additionalSection->rDataLen);
	    		memcpy(*buffer, &packet->MX_additionalSection->rData, 4);//一个int占四个字节 
	  			*buffer += 4;
	  			length += 4;
				return;
			}
			encode_domain_name(buffer, packet->A_additionalSection->dname);
	    	put16bits(buffer, packet->A_additionalSection->type);
	    	put16bits(buffer, packet->A_additionalSection->_class);
	    	put32bits(buffer, packet->A_additionalSection->ttl);
	    	put16bits(buffer, packet->A_additionalSection->rDataLen);
	    	memcpy(*buffer, &packet->A_additionalSection->rData, 4);//一个int占四个字节 
	  		*buffer += 4;
	  		length += 4;
		}
	}
	void char_encode_resource_records(struct packet* packet, char** buffer, int flag) {
		
	  	if(flag == 0){
	  		if(packet->querySection->qType == 15){
	  			encode_domain_name(buffer, packet->MX_answerSection->dname);
	    		put16bits(buffer, packet->MX_answerSection->type);
	    		put16bits(buffer, packet->MX_answerSection->_class);
	    		put32bits(buffer, packet->MX_answerSection->ttl);
	    		put16bits(buffer, packet->MX_answerSection->rDataLen);
	    		encode_domain_name(buffer, packet->MX_answerSection->rData);
	    		return;
			  }
	  		encode_domain_name(buffer, packet->C_answerSection->dname);
	    	put16bits(buffer, packet->C_answerSection->type);
	    	put16bits(buffer, packet->C_answerSection->_class);
	    	put32bits(buffer, packet->C_answerSection->ttl);
	    	put16bits(buffer, packet->C_answerSection->rDataLen);
	    	encode_domain_name(buffer, packet->C_answerSection->rData);
		}
		else if(flag == 1){
			if(packet->querySection->qType == 15){
				encode_domain_name(buffer, packet->MX_authoritySection->dname);
	    		put16bits(buffer, packet->MX_authoritySection->type);
	    		put16bits(buffer, packet->MX_authoritySection->_class);
	    		put32bits(buffer, packet->MX_authoritySection->ttl);
	    		put16bits(buffer, packet->MX_authoritySection->rDataLen);
	    		encode_domain_name(buffer, packet->MX_authoritySection->rData);
				return;
			}
			encode_domain_name(buffer, packet->C_authoritySection->dname);
	    	put16bits(buffer, packet->C_authoritySection->type);
	    	put16bits(buffer, packet->C_authoritySection->_class);
	    	put32bits(buffer, packet->C_authoritySection->ttl);
	    	put16bits(buffer, packet->C_authoritySection->rDataLen);
	    	encode_domain_name(buffer, packet->C_authoritySection->rData);
		}
		else{
			encode_domain_name(buffer, packet->C_answerSection->dname);
	    	put16bits(buffer, packet->C_answerSection->type);
	    	put16bits(buffer, packet->C_answerSection->_class);
	    	put32bits(buffer, packet->C_answerSection->ttl);
	    	put16bits(buffer, packet->C_answerSection->rDataLen);
	    	encode_domain_name(buffer, packet->C_answerSection->rData);
		}	  
	}
	void decode_packet(struct packet* packet, char** buffer) {
		
	  decode_header(packet->header, buffer);
	  packet->querySection->qName = decode_domain_name(buffer);
	  packet->querySection->qType = get16bits(buffer);
	  packet->querySection->qClass = get16bits(buffer);
	  
	  packet->A_answerSection = NULL;
	  packet->A_authoritySection = NULL;
	  packet->A_additionalSection = NULL;
	  
	  packet->C_answerSection = NULL;
	  packet->C_authoritySection = NULL;
	  packet->C_additionalSection = NULL;
	  
	  packet->MX_answerSection = NULL;
	  packet->MX_authoritySection = NULL;
	  packet->MX_additionalSection = NULL;
	}
	void decode_header(struct dnsHeader* hd, char** buffer) {
		
	  hd->id = get16bits(buffer);
	  hd->tag = get16bits(buffer);
	  hd->queryNum = get16bits(buffer);
	  hd->answerNum = get16bits(buffer);
	  hd->authorNum = get16bits(buffer);
	  hd->addNum = get16bits(buffer);
	}
	//域名解码 
	char* decode_domain_name(char** buffer) {
		
	  char* pareseDomain = (char *)malloc((sizeof(char) * BUF_SIZE));//申请空间 
	  char* temp = pareseDomain;//传递最开始指针指向的位置 
	  memset(pareseDomain, 0, BUF_SIZE);//初始化 
	  
	  while(**buffer != 0) {
	    int len = (int)**buffer;//读取每个域的长度 
	    *buffer += 1;
	    memcpy(pareseDomain, *buffer, len);
	    *buffer += len;
	    pareseDomain += len;
	    *pareseDomain = '.';
	    pareseDomain += 1;
	  }
	  *buffer += 1;
	  pareseDomain -= 1;//把多余的.去掉 
	  *pareseDomain = '\0';
	  
	  return temp;
	}
	int getRR (struct dnsRR_A* p, char* fpath,char* qName, unsigned short qType) {
	
	  char* buf = (char *)malloc(sizeof(char) * BUF_SIZE);
	  memset(buf, '\0', BUF_SIZE); 
	  FILE * fp = fopen(fpath, "r");
	  
	  if (fp == NULL) {
	    printf("Can not read the resource record.");
	    exit(0);
	  }
	  while (fgets(buf, BUFSIZ, fp) != NULL) {
	    int len = strlen(buf);//不计算结束字符‘\0’进去 
	    buf[len-1] = '\0';//最后一位为换行符,所以要变成结束符号 
	    len = strlen(buf);
	    
	    p->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
	    memset(p->dname, '\0', BUF_SIZE);
	    //读取顶级域名 
	    
	    int i = 0;
	    for (i; i < len; i++) {
	      if (buf[i] == '#') {
	        memcpy(p->dname, buf, i);//com org us cn
	        buf = buf + i + 1;
	        i=0;
	        break;
	      }
	    }
	    
	    len = strlen(buf);
	    char* temp_type = (char *)malloc(sizeof(char) * BUF_SIZE);
	    memset(temp_type, '\0', BUF_SIZE);
	  	//读取类型 
	    for (i; i < len; i++) {
	      if (buf[i] == '#') {
	      	memcpy(temp_type, buf, i);
	        p->type = getType(temp_type);
	        buf += i + 1;
	        i=0;
	        break;
	      }
	    }
	    
	    len = strlen(buf);
	    //读取特殊码
	    unsigned short prfrc;
	    char* tempPrfrc = (char*)malloc(sizeof(char) * BUF_SIZE);
	    memset(tempPrfrc, 0, BUF_SIZE);
	    
	    for (i; i < len; i++) {
	      if (buf[i] == '#') {
	      	memcpy(tempPrfrc, buf, i);
	        prfrc = atoi(tempPrfrc);
	        buf += i + 1;
	        i=0;
	        break;
	      }
	    }
	    //读取不同类型对应的记录rdata。 
	    if (qType == 1) {
	      parse_A_rData(p, buf);
	    } 
		else if (qType == 5) {
	      parse_CNAME_rData(p, buf);
	    } 
		else {
	      parse_MX_rData(p, buf, prfrc);
	    }
	    
	    p->_class = 0x1;
	    p->ttl = 100;
	    //修正 
	    if(existRR(qName,qType,p)){
	    	return 1;
		}
		else{
			memset(p, 0, sizeof(struct dnsRR_A *));//初始化RR指针.
		} 
	  }
	  fclose(fp);
	  return 0;
	}
	int existRR(char* qName, unsigned short qType, struct dnsRR_A* rr){
		if(rr->type == qType && !strcmp(qName, rr->dname)) {
      		return 1;
    	}
    	else{
    		return 0;
		}
	}
	void parse_A_rData(struct dnsRR_A* p, char* buffer) {
	  
	  char * IP = (char*)malloc(sizeof(char)*BUF_SIZE);
  	  memset(IP, '\0', BUF_SIZE);
	  int i = 0;
	  
	  unsigned int temp = 0;
	  struct in_addr ip;
	  memset(&ip, 0, sizeof(struct in_addr));
	  while(buffer[i] != '\0'){
	  	if(buffer[i] == '!'){
	  		memcpy(IP, buffer, i);
	  		*buffer += i;
	  		inet_aton(IP,&ip);
	  		temp = ip.s_addr;
		  }
		i++;
	  }
	  p->rData = temp;
  	  p->rDataLen = 4; 
	}
	void parse_CNAME_rData(struct dnsRR_A* p, char* buffer) {
	  
	  char * IP = (char*)malloc(sizeof(char)*BUF_SIZE);
  	  memset(IP, '\0', BUF_SIZE);
	  int i = 0;
	  
	  unsigned int temp = 0;
	  struct in_addr ip;
	  memset(&ip, 0, sizeof(struct in_addr));
	  
	  while(buffer[i] != '\0'){
	  	if(buffer[i] == '!'){
	  		memcpy(IP, buffer, i);
	  		*buffer += i;
	  		inet_aton(IP,&ip);
	  		temp = ip.s_addr;
		  }
		i++;
	  }
	  p->rData = temp;
  	  p->rDataLen = 4; 
	}
	void parse_MX_rData(struct dnsRR_A* p, char* buffer, unsigned short prfrc) {
	  
	  char * IP = (char*)malloc(sizeof(char)*BUF_SIZE);
  	  memset(IP, '\0', BUF_SIZE);
	  int i = 0;
	  
	  unsigned int temp = 0;
	  struct in_addr ip;
	  memset(&ip, 0, sizeof(struct in_addr));
	  while(buffer[i] != '\0'){
	  	if(buffer[i] == '!'){
	  		memcpy(IP, buffer, i);
	  		*buffer += i;
	  		inet_aton(IP,&ip);
	  		temp = ip.s_addr;
		  }
		i++;
	  }
	  p->rData = temp;
  	  p->rDataLen = 4; 
	}
	void printQpacket(struct packet packet){
		  printf("[DNS Header]\n");
		  printf("ID\t:\t\t%d\n", packet.header->id);
		  printf("TAG\t:\t\t%d\n", packet.header->tag);
		  printf("Query Number\t:\t\t%d\n", packet.header->queryNum);
		  printf("Answer Number\t:\t\t%d\n", packet.header->answerNum);
		  printf("Authority Number\t:\t\t%d\n", packet.header->authorNum);
		  printf("Additional Number\t:\t\t%d\n", packet.header->addNum);
		  printf("[Query Section]\n");
		  printf("Query Name\t:\t\t%s\n", packet.querySection->qName);
		  printf("Query Type\t:\t\t%d\n", packet.querySection->qType);//转化为英文 
		  printf("Query Class\t:\t\t%d\n", packet.querySection->qClass);
		  printf("---------------------------------\n");
	}
	void printApacket(struct packet packet){
		
	}
