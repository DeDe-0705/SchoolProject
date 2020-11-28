	#include <stdio.h> /* for printf() and fprintf() */ 
	#include <sys/socket.h> /* for socket(), sendto() and recvfrom() */ 
	#include <arpa/inet.h> /* for sockaddr_in and inet_addr() */ 
	#include <stdlib.h> /* for atoi() and exit() */ 
	#include <string.h> /* for memset() */ 
	#include <unistd.h> /* for close() */
	#define SERVER_PORT 53
	#define BUF_SIZE 1024 
	
	unsigned short length = 0;
	char* printf_IP;
	char* printf_Name;
		
	enum tag_type {
	  standard_query_NRD = 0x0000,    
	  standard_res_NAA_NRA = 0x8000, 
	  standard_res_AA_NRA = 0x8400,    
	  name_wrong_res = 0x8403,        
	  format_wrong_res = 0x8401        
	} tag;

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
	
	void put16bits(char** buffer, unsigned short value);
	unsigned short get16bits(char** buffer);
	void put32bits(char** buffer, unsigned int value);
	unsigned int get32bits(char** buffer);
	unsigned short getType(char *type);
	
	void encode_packet(struct packet* packet, char** buffer);
	void encode_header(struct dnsHeader* hd, char** buffer); 
	void encode_domain_name(char** buffer, char* domain); 
	
	void decode_packet(struct packet* packet, char** buffer);
	void decode_header(struct dnsHeader* hd, char** buffer);
	char* decode_domain_name(char** buffer);
	void char_decode_resource_records(struct packet* packet, char** buffer, int flag);
	void int_decode_resource_records(struct packet* packet, char** buffer, int flag);
	
	void printQpacket(struct packet packet);
	void printApacket(struct packet packet);
	
	int main(int argc,char *argv[]) {
	
		int sock; 
		struct sockaddr_in LocalServAddr;
		struct packet qPacket;  
		struct packet aPacket;
		
		char* buf;
		char* send_buf; 
  		char* rec_buf;
		
		char* Server_IP = "127.0.0.2";
		int nbytes;
		unsigned short rec_length;
		
		if (argc < 2 || argc > 3) { 
		    printf("Usage: ./client domain_name type\n"); 
		    exit(1); 
		}
		
		if ((sock = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
			printf("socket() failed.\n"); 
			exit(1);
		}
		memset(&LocalServAddr, 0, sizeof(LocalServAddr));
		LocalServAddr.sin_family = AF_INET;  
		LocalServAddr.sin_addr.s_addr = inet_addr(Server_IP);
		LocalServAddr.sin_port = htons(SERVER_PORT); 
		if (connect(sock, (struct sockaddr*)&LocalServAddr, sizeof(struct sockaddr)) == -1) { 
		    printf("TCP connection failed\n");
		    exit(1); 
	  	}
		
		initPacket(&qPacket, 0);
		qPacket.header->id = 0x1;
		qPacket.header->tag = standard_query_NRD;
		qPacket.header->queryNum = 0x0001;
		qPacket.header->answerNum = 0x0000; 
		qPacket.header->authorNum = 0x0000;
		qPacket.header->addNum = 0x0000;
		qPacket.querySection->qName = argv[1]; 
		qPacket.querySection->qType = getType(argv[2]);  
		qPacket.querySection->qClass = 0x0001; 
		
	  	send_buf = (char*) malloc(sizeof(char) * BUF_SIZE); 
	  	memset(send_buf, '\0', BUF_SIZE);
	   
	 	buf = send_buf;
	  	send_buf += 2;
	  	encode_packet(&qPacket, &send_buf);
	  	length = htons(length); 
	  	memcpy(buf, &length, sizeof(length));
	  	length = ntohs(length);
	  	if (send(sock, buf, length+2, 0) == -1) {  
		    printf("Something wrong with socket sending packet\n");
		    exit(1); 
	  	}
	  	printQpacket(qPacket);
	  	
  		rec_buf = (char *)malloc(sizeof(char) * BUF_SIZE);  
  		memset(rec_buf, '\0', BUF_SIZE);
	  	if ((nbytes = recv(sock, rec_buf, BUF_SIZE, 0)) == -1) {
	      	printf("Something wrong with socket receving\n");
	  	}
	  	initPacket(&aPacket, 1);
	  	memcpy(&rec_length, rec_buf, sizeof(rec_length));
	  	rec_length = ntohs(rec_length);
	  	rec_length += 2;
	  	
		if(rec_length!=nbytes){
	  		printf("Error!");
	  		exit(1);
		}
	  	rec_buf += 2; 
	  	decode_packet(&aPacket, &rec_buf);
	  	if (aPacket.header->tag == name_wrong_res) {
	    	printf("no such domian name\n");
	  	} 
		else {
	    	printApacket(aPacket); 
	  	} 
	  	return 0;
	}
	
	void put16bits(char** buffer, unsigned short value) {
		
	  value = htons(value);
	  memcpy(*buffer, &value, 2);
	  *buffer += 2;
	  length += 2;
	}
	unsigned short get16bits(char** buffer) {
		
	  unsigned short value;
	  memcpy(&value, *buffer, 2);
	  *buffer += 2;
	  return ntohs(value);
	}
	void put32bits(char** buffer, unsigned int value) {
		
	  value = htonl(value);
	  memcpy(*buffer, &value, 4);
	  *buffer += 4;
	  length += 4;
	}
	unsigned int get32bits(char** buffer) {
		
	  unsigned int value;
	  memcpy(&value, *buffer, 4);
	  *buffer += 4;
	  return ntohl(value);
	}
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
	void encode_packet(struct packet* packet, char** buffer) {
	  
	  encode_header(packet->header, buffer);
	  encode_domain_name(buffer, packet->querySection->qName);
	  put16bits(buffer, packet->querySection->qType);
	  put16bits(buffer, packet->querySection->qClass);
	  
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
	void encode_header(struct dnsHeader* hd, char** buffer) {
		
	  put16bits(buffer, hd->id);
	  put16bits(buffer, hd->tag);
	  put16bits(buffer, hd->queryNum);
	  put16bits(buffer, hd->answerNum);
	  put16bits(buffer, hd->authorNum);
	  put16bits(buffer, hd->addNum);
	}
	void encode_domain_name(char** buffer, char* domain) {
		
	  int j = -1;
	  do {
	    j++;
	    if (domain[j] == '.' || domain[j] == '\0') {
	      **buffer = j;
	      *buffer += 1;
	      length += 1;
	      memcpy(*buffer, domain, j);
	      *buffer += j;
	      length += j;
	      domain = domain + j + 1;
	      j = -1;
	    }
	  } while (domain[j] != '\0');
	  **buffer = 0;
	  *buffer += 1;
	  length += 1;
	}
	void decode_packet(struct packet* packet, char** buffer) {
		
	  decode_header(packet->header, buffer);
	  packet->querySection->qName = decode_domain_name(buffer);
	  packet->querySection->qType = get16bits(buffer);
	  packet->querySection->qClass = get16bits(buffer);
	  
	  if (packet->header->answerNum != 0) {
	  	if(packet->querySection->qType == 1){
	  		int_decode_resource_records(packet, buffer, 0);
		  }
		else if(packet->querySection->qType == 5){
			char_decode_resource_records(packet, buffer, 0);
		} 
		else{
			char_decode_resource_records(packet, buffer, 0);
		}
	  } 
	  else {
	    packet->A_answerSection = NULL;
	    packet->C_answerSection = NULL;
	    packet->MX_answerSection = NULL;
	  }
	  if (packet->header->authorNum != 0) {
	  	if(packet->querySection->qType == 1){
	  		int_decode_resource_records(packet, buffer, 1);
		  }
		else if(packet->querySection->qType == 5){
			char_decode_resource_records(packet, buffer, 1);
		}
		else{
			char_decode_resource_records(packet, buffer, 1);
		}
	  } 
	  else {
	    packet->A_authoritySection = NULL;
	    packet->C_authoritySection = NULL;
	    packet->MX_authoritySection = NULL;
	  }
	  if (packet->header->addNum != 0) {
	  	if(packet->querySection->qType == 1){
	  		int_decode_resource_records(packet, buffer, 2);
		  }
		else if(packet->querySection->qType == 5){
			char_decode_resource_records(packet, buffer, 2);
		}
		else{
			int_decode_resource_records(packet, buffer, 2);
		}
	  } 
	  else {
	    packet->A_additionalSection = NULL;
	    packet->C_additionalSection = NULL;
	    packet->MX_additionalSection = NULL;
	  }
	}
	void decode_header(struct dnsHeader* hd, char** buffer) {
		
	  hd->id = get16bits(buffer);
	  hd->tag = get16bits(buffer);
	  hd->queryNum = get16bits(buffer);
	  hd->answerNum = get16bits(buffer);
	  hd->authorNum = get16bits(buffer);
	  hd->addNum = get16bits(buffer);
	}
	char* decode_domain_name(char** buffer) {
		
	  char* pareseDomain = (char *)malloc((sizeof(char) * BUF_SIZE)); 
	  char* temp = pareseDomain;
	  memset(pareseDomain, 0, BUF_SIZE);
	  
	  while(**buffer != 0) {
	    int len = (int)**buffer;
	    *buffer += 1;
	    memcpy(pareseDomain, *buffer, len);
	    *buffer += len;
	    pareseDomain += len;
	    *pareseDomain = '.';
	    pareseDomain += 1;
	  }
	  *buffer += 1;
	  pareseDomain -= 1; 
	  *pareseDomain = '\0';
	  
	  return temp;
	}
	void int_decode_resource_records(struct packet* packet, char** buffer, int flag) {
	  
	  unsigned int value = 0;
	  if(flag == 0){
	  	packet->A_answerSection->dname = decode_domain_name(buffer);
	  	packet->A_answerSection->type = get16bits(buffer);
	  	packet->A_answerSection->_class = get16bits(buffer);
	 	packet->A_answerSection->ttl = get32bits(buffer);
	  	packet->A_answerSection->rDataLen = get16bits(buffer);
	  	
	  	memcpy(&value, *buffer, 4);
	  	*buffer += 4;
	  	packet->A_answerSection->rData = value;
	  	
	  	struct in_addr ip;
	  	memset(&ip,0,sizeof(struct in_addr));
	  	ip.s_addr = value;
	  	printf_IP = inet_ntoa(ip);
	  }
	  else if(flag == 1){
	  	packet->A_authoritySection->dname = decode_domain_name(buffer);
	  	packet->A_authoritySection->type = get16bits(buffer);
	  	packet->A_authoritySection->_class = get16bits(buffer);
	 	packet->A_authoritySection->ttl = get32bits(buffer);
	  	packet->A_authoritySection->rDataLen = get16bits(buffer);
	  	
	  	memcpy(&value, *buffer, 4);
	  	*buffer += 4;
	  	packet->A_authoritySection->rData = value;
	  	
	  	struct in_addr ip;
	  	memset(&ip,0,sizeof(struct in_addr));
	  	ip.s_addr = value;
	  	printf_IP = inet_ntoa(ip);
	  }
	  else{
	  	if(packet->querySection->qType == 15){
	  		packet->A_additionalSection->dname = decode_domain_name(buffer);
	  		packet->A_additionalSection->type = get16bits(buffer);
	  		packet->A_additionalSection->_class = get16bits(buffer);
	 		packet->A_additionalSection->ttl = get32bits(buffer);
	  		packet->A_additionalSection->rDataLen = get16bits(buffer);
	  		
	  		memcpy(&value, *buffer, 4);
	  		*buffer += 4;
	  		packet->A_additionalSection->rData = value;
	  		
	  		struct in_addr ip;
	  		memset(&ip,0,sizeof(struct in_addr));
	  		ip.s_addr = value;
	  		printf_IP = inet_ntoa(ip);
	  		return;
	  		
		  }
		  
	  	packet->A_additionalSection->dname = decode_domain_name(buffer);
	  	packet->A_additionalSection->type = get16bits(buffer);
	  	packet->A_additionalSection->_class = get16bits(buffer);
	 	packet->A_additionalSection->ttl = get32bits(buffer);
	  	packet->A_additionalSection->rDataLen = get16bits(buffer);
	  	
	  	memcpy(&value, *buffer, 4);
	  	*buffer += 4;
	  	packet->A_additionalSection->rData = value;
	  	
	  	struct in_addr ip;
	  	memset(&ip,0,sizeof(struct in_addr));
	  	ip.s_addr = value;
	  	printf_IP = inet_ntoa(ip);
	  } 
	}
	void char_decode_resource_records(struct packet* packet, char** buffer, int flag) {
	  
	  if(flag == 0){
	  	if(packet->querySection->qType == 15){
	  		packet->MX_answerSection->dname = decode_domain_name(buffer);
	  		packet->MX_answerSection->type = get16bits(buffer);
	  		packet->MX_answerSection->_class = get16bits(buffer);
	 		packet->MX_answerSection->ttl = get32bits(buffer);
	  		packet->MX_answerSection->rDataLen = get16bits(buffer);
	  		*buffer+=2;//preference
	  		packet->MX_answerSection->rData = decode_domain_name(buffer);
	  		printf_Name = packet->MX_answerSection->rData;
	  		return;
		  }
	  	packet->C_answerSection->dname = decode_domain_name(buffer);
	  	packet->C_answerSection->type = get16bits(buffer);
	  	packet->C_answerSection->_class = get16bits(buffer);
	 	packet->C_answerSection->ttl = get32bits(buffer);
	  	packet->C_answerSection->rDataLen = get16bits(buffer);
	  	packet->C_answerSection->rData = decode_domain_name(buffer);
	  	printf_Name = packet->C_answerSection->rData;
	  }
	  else if(flag == 1){
	  	packet->C_authoritySection->dname = decode_domain_name(buffer);
	  	packet->C_authoritySection->type = get16bits(buffer);
	  	packet->C_authoritySection->_class = get16bits(buffer);
	 	packet->C_authoritySection->ttl = get32bits(buffer);
	  	packet->C_authoritySection->rDataLen = get16bits(buffer);
	  	packet->C_authoritySection->rData = decode_domain_name(buffer);
	  	printf_Name = packet->C_authoritySection->rData;
	  }
	  else{
	  	packet->C_additionalSection->dname = decode_domain_name(buffer);
	  	packet->C_additionalSection->type = get16bits(buffer);
	  	packet->C_additionalSection->_class = get16bits(buffer);
	 	packet->C_additionalSection->ttl = get32bits(buffer);
	  	packet->C_additionalSection->rDataLen = get16bits(buffer);
	  	packet->C_additionalSection->rData = decode_domain_name(buffer);
	  	printf_Name = packet->C_additionalSection->rData;
	  }
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
	void printQpacket(struct packet packet){
		  printf("Send: \n");
		  printf("[DNS Header]\n");
		  printf("ID\t:\t\t%d\n", packet.header->id);
		  printf("TAG\t:\t\t%d\n", packet.header->tag);
		  printf("Query Number\t:\t\t%d\n", packet.header->queryNum);
		  printf("Answer Number\t:\t\t%d\n", packet.header->answerNum);
		  printf("Authority Number\t:\t\t%d\n", packet.header->authorNum);
		  printf("Additional Number\t:\t\t%d\n", packet.header->addNum);
		  printf("[Query Section]\n");
		  printf("Query Name\t:\t\t%s\n", packet.querySection->qName);
		  printf("Query Type\t:\t\t%d\n", packet.querySection->qType);
		  printf("Query Class\t:\t\t%d\n", packet.querySection->qClass);
		  printf("---------------------------------\n");
	}
	void printApacket(struct packet packet){
		  printf("Response: \n");
		  printf("[DNS Header]\n");
		  printf("ID\t:\t\t%d\n", packet.header->id);
		  printf("TAG\t:\t\t%d\n", packet.header->tag);
		  printf("Query Number\t:\t\t%d\n", packet.header->queryNum);
		  printf("Answer Number\t:\t\t%d\n", packet.header->answerNum);
		  printf("Authority Number\t:\t\t%d\n", packet.header->authorNum);
		  printf("Additional Number\t:\t\t%d\n", packet.header->addNum);
		  printf("[Query Section]\n");
		  printf("Query Name\t:\t\t%s\n", packet.querySection->qName);
		  printf("Query Type\t:\t\t%d\n", packet.querySection->qType);
		  printf("Query Class\t:\t\t%d\n", packet.querySection->qClass);
		  
		  printf("[RESOURCE RECORDS]\n");
		  printf("Answer Section:\n");
		  if (packet.header->answerNum != 0) {
		  	if(packet.querySection->qType == 1){
		  		printf("Name\t:\t\t%s\n", packet.A_answerSection->dname);
		    	printf("Type\t:\t\t%d\n", packet.A_answerSection->type);
		    	printf("Class\t:\t\t%d\n", packet.A_answerSection->_class);
		    	printf("Time to left\t:\t\t%d\n", packet.A_answerSection->ttl);
		    	printf("Data length\t:\t\t%d\n", packet.A_answerSection->rDataLen);
		    	printf("Data\t:\t\t%s\n", printf_IP);
			}
			else if(packet.querySection->qType == 5){
				printf("Name\t:\t\t%s\n", packet.C_answerSection->dname);
		    	printf("Type\t:\t\t%d\n", packet.C_answerSection->type);
		    	printf("Class\t:\t\t%d\n", packet.C_answerSection->_class);
		    	printf("Time to left\t:\t\t%d\n", packet.C_answerSection->ttl);
		    	printf("Data length\t:\t\t%d\n", packet.C_answerSection->rDataLen);
		    	printf("Data\t:\t\t%s\n", packet.C_answerSection->rData);
			}
			else{
				printf("Name\t:\t\t%s\n", packet.MX_answerSection->dname);
		    	printf("Type\t:\t\t%d\n", packet.MX_answerSection->type);
		    	printf("Class\t:\t\t%d\n", packet.MX_answerSection->_class);
		    	printf("Time to left\t:\t\t%d\n", packet.MX_answerSection->ttl);
		    	printf("Data length\t:\t\t%d\n", packet.MX_answerSection->rDataLen);
		    	printf("Data\t:\t\t%s\n", packet.MX_answerSection->rData);
			}
		  }
		  printf("Autority Section:\n");
		  if(packet.header->authorNum != 0){
		  		if(packet.querySection->qType == 1){
		  			printf("Name\t:\t\t%s\n", packet.A_authoritySection->dname);
		    		printf("Type\t:\t\t%d\n", packet.A_authoritySection->type);
		    		printf("Class\t:\t\t%d\n", packet.A_authoritySection->_class);
		    		printf("Time to left\t:\t\t%d\n", packet.A_authoritySection->ttl);
		    		printf("Data length\t:\t\t%d\n", packet.A_authoritySection->rDataLen);
		    		printf("Data\t:\t\t%s\n", printf_IP);
				}
				else if(packet.querySection->qType == 5){
					printf("Name\t:\t\t%s\n", packet.C_authoritySection->dname);
		    		printf("Type\t:\t\t%d\n", packet.C_authoritySection->type);
		    		printf("Class\t:\t\t%d\n", packet.C_authoritySection->_class);
		    		printf("Time to left\t:\t\t%d\n", packet.C_authoritySection->ttl);
		    		printf("Data length\t:\t\t%d\n", packet.C_authoritySection->rDataLen);
		    		printf("Data\t:\t\t%s\n", packet.C_authoritySection->rData);
				}
				else{
					printf("Name\t:\t\t%s\n", packet.MX_authoritySection->dname);
		    		printf("Type\t:\t\t%d\n", packet.MX_authoritySection->type);
		    		printf("Class\t:\t\t%d\n", packet.MX_authoritySection->_class);
		    		printf("Time to left\t:\t\t%d\n", packet.MX_authoritySection->ttl);
		    		printf("Data length\t:\t\t%d\n", packet.MX_authoritySection->rDataLen);
		    		printf("Data\t:\t\t%s\n", packet.MX_authoritySection->rData);
				}
		  		
		  }
		  
		  printf("Addtional Section:\n");
		  if (packet.header->addNum != 0) {
		  	if(packet.querySection->qType == 1){
		  		printf("Name\t:\t\t%s\n", packet.A_additionalSection->dname);
		    	printf("Type\t:\t\t%d\n", packet.A_additionalSection->type);
		    	printf("Class\t:\t\t%d\n", packet.A_additionalSection->_class);
		    	printf("Time to left\t:\t\t%d\n", packet.A_additionalSection->ttl);
		    	printf("Data length\t:\t\t%d\n", packet.A_additionalSection->rDataLen);
		    	printf("Data\t:\t\t%s\n", printf_IP);
			}
			else if(packet.querySection->qType == 5){
				printf("Name\t:\t\t%s\n", packet.C_additionalSection->dname);
		    	printf("Type\t:\t\t%d\n", packet.C_additionalSection->type);
		    	printf("Class\t:\t\t%d\n", packet.C_additionalSection->_class);
		    	printf("Time to left\t:\t\t%d\n", packet.C_additionalSection->ttl);
		    	printf("Data length\t:\t\t%d\n", packet.C_additionalSection->rDataLen);
		    	printf("Data\t:\t\t%s\n", packet.C_additionalSection->rData);
			}
			else{
				printf("Name\t:\t\t%s\n", packet.A_additionalSection->dname);
		    	printf("Type\t:\t\t%d\n", packet.A_additionalSection->type);
		    	printf("Class\t:\t\t%d\n", packet.A_additionalSection->_class);
		    	printf("Time to left\t:\t\t%d\n", packet.A_additionalSection->ttl);
		    	printf("Data length\t:\t\t%d\n", packet.A_additionalSection->rDataLen);
		    	printf("Data\t:\t\t%s\n", printf_IP);
			}
		  }
	}
