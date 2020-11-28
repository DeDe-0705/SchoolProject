#include <stdio.h>
#include <sys/socket.h>
#include <netinet/tcp.h>
#include <time.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#define SERVER_PORT 53
#define UDP_PORT 53
#define BUF_SIZE 1024
#define BACKLOG 10

unsigned short length = 0;
unsigned short query_domain_length = 0;

unsigned int jump_1 = 0;
unsigned int jump_2 = 0;
unsigned int jump_3 = 0;

unsigned int mark = 0;
char* printf_IP;
char* printf_Name;

enum tag_type {
    standard_query_NRD = 0x0000,
    standard_query_RD = 0x0100,
    inverse_query_NRD = 0x0800,
    inverse_query_RD = 0x0900,
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

struct list{
		struct dnsRR_A* A;
		struct dnsRR_CNAME* C;
		struct dnsRR_MX* M;
	};
	
struct packet {
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

struct dnsHeader {
    unsigned short id;
    unsigned short tag; 
    unsigned short queryNum;
    unsigned short answerNum;
    unsigned short authorNum;
    unsigned short addNum;
};


struct dnsQuery {
    char *qName;
    unsigned short qType;//A MX CNEMA
    unsigned short qClass;
};


struct dnsRR_A {
    char *dname;
    unsigned short type;
    unsigned short _class;
    unsigned int ttl;
    unsigned short rDataLen;
    unsigned int rData;
};

struct dnsRR_MX {
    char *dname;
    unsigned short type;
    unsigned short _class;
    unsigned int ttl;
    unsigned short rDataLen;
    char *rData;
};

struct dnsRR_CNAME {
    char *dname;
    unsigned short type;
    unsigned short _class;
    unsigned int ttl;
    unsigned short rDataLen;
    char *rData;
};

void initPacket(struct packet *Packet, int flag);
void printPacket(struct packet packet);

void put16bits(char** buffer, unsigned short value);
unsigned short get16bits(char** buffer);
void put32bits(char** buffer, unsigned int value);
unsigned int get32bits(char** buffer);
unsigned short getType(char *type);

void encode_packet(struct packet* packet, char** buffer);
void encode_header(struct dnsHeader* hd, char** buffer);
void encode_domain_name(char** buffer, char* domain);
void int_encode_resource_records(struct packet* packet, char** buffer, int flag);
void char_encode_resource_records(struct packet* packet, char** buffer, int flag);

void decode_IP(struct packet* packet, char** buffer); 
void decode_packet(struct packet* packet, char** buffer);
void decode_header(struct dnsHeader* hd, char** buffer);
char* decode_domain_name(char** buffer);
void char_decode_resource_records(struct packet* packet, char** buffer, int flag);
void int_decode_resource_records(struct packet* packet, char** buffer, int flag);

struct list* getRR (int flag, char* fpath,char* qName, unsigned short qType);
void parse_A_rData(struct dnsRR_A* p, char* buffer);
void parse_CNAME_rData(struct dnsRR_CNAME* p, char* buffer);
void parse_MX_rData(struct dnsRR_MX* p, char* buffer, unsigned short prfrc);
int existRR(char* qName, unsigned short qType, char* name, unsigned short type, int flag);

void add_A_RR(struct packet packet, char* fpath);
void add_CNAME_RR (struct packet packet, char* fpath);
void add_MX_RR (struct packet packet, char* fpath);






int main(int argc, char *argv[]) {

    char *serverIP = "127.0.0.2";

    int sockfd, client_fd; 
    struct sockaddr_in my_addr; 
    struct sockaddr_in remote_addr; 

    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
        printf("Something wrong with socket creation\n");
        exit(1);
    }

    
    memset(&my_addr, 0, sizeof(my_addr));
    my_addr.sin_family = AF_INET;
    my_addr.sin_port = htons(SERVER_PORT);
    my_addr.sin_addr.s_addr = inet_addr(serverIP);

    
    if (bind(sockfd, (struct sockaddr *) &my_addr, sizeof(struct sockaddr)) == -1) {
        printf("Something wrong with socket binding\n");
        exit(1);
    }
    
    if (listen(sockfd, BACKLOG) == -1) {
        printf("Something wrong with socket listening\n");
        exit(1);
    }

    
    int sock;
    if ((sock = socket(PF_INET, SOCK_DGRAM, 0)) < 0) {
        printf("Something wrong with socket creation\n");
        exit(1);
    }
    if (bind(sock, (struct sockaddr *) &my_addr, sizeof(struct sockaddr)) == -1) {
        printf("Something wrong with socket binding\n");
        exit(1);
	}
        while (1) {
            unsigned int sin_size = sizeof(struct sockaddr_in);
            if ((client_fd = (accept(sockfd, (struct sockaddr *) &remote_addr, &sin_size))) == -1){
                printf("Something wrong with socket accepting\n");
                continue;
            }

            //buffer initialization
            char *buf;
            buf = (char *) malloc(sizeof(char) * BUF_SIZE);
            buf = memset(buf, 0, BUF_SIZE);
            int nbytes;

            printf("received a query from %s\n", inet_ntoa(remote_addr.sin_addr));
            if ((nbytes = recv(client_fd, buf, BUF_SIZE, 0)) == -1) {
                printf("Something wrong with socket receving\n");
			}

            
            struct packet qPacket;
            unsigned short rec_length = 0;
            
            initPacket(&qPacket, 0);
            memcpy(&rec_length, buf, sizeof(rec_length));
            rec_length = ntohs(rec_length);
            rec_length += 2;
            if(rec_length!=nbytes){
	  			printf("Error!");
	  			exit(1);
			}
			
            buf += 2;
            decode_packet(&qPacket, &buf);

            printf("query domain name: %s\n", qPacket.querySection->qName);
            printf("query type: %d\n", qPacket.querySection->qType);
			
			struct dnsRR_A* aRR = (struct dnsRR_A*)malloc(sizeof(struct dnsRR_A));
	        struct dnsRR_CNAME* cRR = (struct dnsRR_CNAME*)malloc(sizeof(struct dnsRR_CNAME));
	        struct dnsRR_MX* mRR = (struct dnsRR_MX*)malloc(sizeof(struct dnsRR_MX));
			    		
    		struct dnsQuery* query = qPacket.querySection;
    		
    		struct list* RR =  (struct list*)malloc(sizeof(struct list));
			struct list* RR_1 = (struct list*)malloc(sizeof(struct list));
			struct list* RR_2 = (struct list*)malloc(sizeof(struct list));
			
			int flag = 0;
    		if(query->qType == 1){
    			
    			RR = getRR(0, "./resource_records.txt",query->qName,query->qType);
    			if(mark == 1){
    				flag = 1;
    				mark = 0;
				}
				else{
					flag = 0;	
				}
			}
			else if(query->qType == 5){
				RR = getRR(1, "./resource_records.txt",query->qName,query->qType);
				if(mark == 2){
    				flag = 2;
    				mark = 0;
				}
				else{
					flag = 0;
				}
			}
			else{
				RR_1 = getRR(2, "./resource_records.txt",query->qName,query->qType);
				if(mark == 3){
    				RR_2 = getRR(0, "./resource_records.txt",RR_1->M->rData,query->qType);
    				flag = 3;
    				mark = 0;
				}
				else{
					flag = 0;
				}
			}
			
			struct packet aPacket;
    		initPacket(&aPacket, 1);
			int key = 0;
			
			if(flag == 1){
    			aRR = RR->A;
    			aPacket.header->id = 0x0005;
			    aPacket.header->tag = standard_res_AA_NRA;
			    aPacket.header->queryNum = 0x0001;
			    aPacket.header->answerNum = 0x0001;
			    aPacket.header->addNum = 0x0000;
			    aPacket.header->authorNum = 0x0000;
			    aPacket.querySection = query;
			    
			    aPacket.A_answerSection = aRR;
			    aPacket.A_authoritySection = NULL;
			    aPacket.A_additionalSection = NULL;
			    aPacket.C_answerSection = NULL;
			    aPacket.C_authoritySection = NULL;
			    aPacket.C_additionalSection = NULL;
			    aPacket.MX_answerSection = NULL;
			    aPacket.MX_authoritySection = NULL;
			    aPacket.MX_additionalSection = NULL;
			    
			    aPacket.A_answerSection->dname = query->qName;
			}
			else if(flag == 2){
				cRR = RR->C;
				aPacket.header->id = 0x0005;
			    aPacket.header->tag = standard_res_AA_NRA;
			    aPacket.header->queryNum = 0x0001;
			    aPacket.header->answerNum = 0x0001;
			    aPacket.header->addNum = 0x0000;
			    aPacket.header->authorNum = 0x0000;
			    aPacket.querySection = query;
			    aPacket.A_answerSection = NULL;
			    aPacket.A_authoritySection = NULL;
			    aPacket.A_additionalSection = NULL;
			    aPacket.C_answerSection = cRR;
			    aPacket.C_authoritySection = NULL;
			    aPacket.C_additionalSection = NULL;
			    aPacket.MX_answerSection = NULL;
			    aPacket.MX_authoritySection = NULL;
			    aPacket.MX_additionalSection = NULL;
			    aPacket.C_answerSection->dname = query->qName;
			}
			else if(flag == 3){
				mRR = RR_1->M;
				aRR = RR_2->A;
				aPacket.header->id = 0x0005;
			    aPacket.header->tag = standard_res_AA_NRA;
			    aPacket.header->queryNum = 0x0001;
			    aPacket.header->answerNum = 0x0001;
			    aPacket.header->addNum = 0x0001;
			    aPacket.header->authorNum = 0x0000;
			    aPacket.querySection = query;
			    aPacket.A_answerSection = NULL;
			    aPacket.A_authoritySection = NULL;
			    aPacket.A_additionalSection = aRR;
			    aPacket.C_answerSection = NULL;
			    aPacket.C_authoritySection = NULL;
			    aPacket.C_additionalSection = NULL;
			    aPacket.MX_answerSection = mRR;
			    aPacket.MX_authoritySection = NULL;
			    aPacket.MX_additionalSection = NULL;
			    aPacket.A_additionalSection->dname = aPacket.MX_answerSection->rData;
			    aPacket.A_additionalSection->type = 0x0001;
			    aPacket.MX_answerSection->dname = query->qName; 
			    aPacket.MX_answerSection->rDataLen += 4; 
			}
			else{
				key = 1;
			}
			
            if(key == 0){
                char *temp_buf = (char *) malloc(sizeof(char) * BUF_SIZE);
                memset(temp_buf, '\0', BUF_SIZE);
                char *send_buf = temp_buf;
                temp_buf += 2;
                encode_packet(&aPacket, &temp_buf);
                printf("send packet to %s\n", inet_ntoa(remote_addr.sin_addr));
                printf("********************************\n");
                printf("find in cache\n");
                printf("********************************\n");
                length = htons(length);
                memcpy(send_buf, &length, sizeof(length));
                length = ntohs(length);

                if (send(client_fd, send_buf, length + 2, 0) == -1) {
                    printf("Something wrong with socket sending packet\n");
                    exit(1);
                }
                length = 0;
                query_domain_length = 0;
			}
			else {
                unsigned short rootServPort;
                char *udp_server_ip = "127.0.0.3"; 
                char *temp_udp_send_buf = (char *) malloc(sizeof(char) * BUF_SIZE);
                struct sockaddr_in udp_server_add;

                memset(temp_udp_send_buf, '\0', BUF_SIZE);
                memset(&udp_server_add, 0, sizeof(udp_server_add));
                
                udp_server_add.sin_family = AF_INET;
                udp_server_add.sin_addr.s_addr = inet_addr(udp_server_ip);
                udp_server_add.sin_port = htons(UDP_PORT);

                struct packet localQueryPacket;
                initPacket(&localQueryPacket, 0);
                localQueryPacket.header = qPacket.header;
                localQueryPacket.querySection = qPacket.querySection;
                
                char *udp_send_buf = temp_udp_send_buf;
                encode_packet(&localQueryPacket, &temp_udp_send_buf);
                long start, end;
                double duration;

                printf("send packet to root: %s\n", udp_server_ip);
                start = clock();
                if ((sendto(sock,udp_send_buf,length,0,(struct sockaddr *) &udp_server_add,sizeof(udp_server_add))) == -1) {
                    printf("Something wrong with socket sending packet\n");
                    exit(1);
                }

                char *udp_rec_buf = (char *) malloc(sizeof(char) * BUF_SIZE);
                memset(udp_rec_buf, '\0', BUF_SIZE);
                char *h_udp_rec_buf = udp_rec_buf; 

                unsigned int remAddrLen = sizeof(udp_server_add);
                if ((recvfrom(sock, udp_rec_buf, BUF_SIZE, 0, (struct sockaddr *) &udp_server_add, &remAddrLen)) == -1) {
                    printf("Something wrong with socket receving\n");
                }
                end = clock();

                struct packet localAnwserPacket;
                initPacket(&localAnwserPacket,1);
                
                decode_IP(&localAnwserPacket, &udp_rec_buf); 
                
                if (localAnwserPacket.header->tag != name_wrong_res) {
                    printf("receive packet from root: %s\n",udp_server_ip);
                } 
				else {
                    printf("receive packet from root: no such domain name\n");
                }
                duration = (double) (end - start) / 1000;
                printf("time duration: %f seconds\n", duration);

                udp_rec_buf = h_udp_rec_buf; 
                memset(udp_rec_buf, '\0', BUF_SIZE);
                
                while (jump_1 == 0 && localAnwserPacket.header->tag != name_wrong_res) {
                    
                    char* udp_server_ip_2 = (char *) malloc(sizeof(char) * BUF_SIZE);
                    memset(udp_server_ip_2, '\0', BUF_SIZE);
                    
                    struct in_addr serverIP;
	  				memset(&serverIP,0,sizeof(struct in_addr));
	  				serverIP.s_addr = localAnwserPacket.A_authoritySection->rData;
	  				udp_server_ip_2 = inet_ntoa(serverIP);
	  	
                    udp_server_add.sin_addr.s_addr = inet_addr(udp_server_ip_2);
                    printf("send packet to %s\n", udp_server_ip_2);
                    start = clock();

                    if ((sendto(sock,udp_send_buf,length,0,(struct sockaddr *) &udp_server_add,sizeof(udp_server_add))) == -1) {
                        printf("Something wrong with socket sending packet\n");
                        exit(1);
                    }


                    initPacket(&localAnwserPacket,1);
                    memset(udp_rec_buf, '\0', BUF_SIZE);
                    
                    if ((recvfrom(sock, udp_rec_buf, BUF_SIZE, 0, (struct sockaddr *) &udp_server_add, &remAddrLen)) == -1) {
                        printf("Something wrong with socket receving\n");
                    }

                    end = clock();
                    decode_IP(&localAnwserPacket, &udp_rec_buf); 
                    if (localAnwserPacket.header->tag != name_wrong_res) {
                        printf("receive packet from Top-level %s\n", udp_server_ip_2);
                        
                    } 
					else {
                        printf("receive packet from %s: no such domain name\n", udp_server_ip);
                    }
                    duration = (double) (end - start) / 1000;

                    printf("time duration: %f seconds\n", duration);

                    udp_rec_buf = h_udp_rec_buf;
                    memset(udp_rec_buf, '\0', BUF_SIZE);
                    jump_1 = 1 ;
                    
					 while(jump_2 == 0 && localAnwserPacket.header->tag != name_wrong_res){
					 	
					 	char* udp_server_ip_3 = (char *) malloc(sizeof(char) * BUF_SIZE);
                    	memset(udp_server_ip_3, '\0', BUF_SIZE);
					 	struct in_addr serverIP_2;
	  					memset(&serverIP_2,0,sizeof(struct in_addr));
	  					serverIP_2.s_addr = localAnwserPacket.A_authoritySection->rData;
	  					udp_server_ip_3 = inet_ntoa(serverIP_2);
	  					
	  					udp_server_add.sin_addr.s_addr = inet_addr(udp_server_ip_3);
                    	printf("send packet to %s\n", udp_server_ip_3);
                    	start = clock();

                    	if ((sendto(sock,udp_send_buf,length,0,(struct sockaddr *) &udp_server_add,sizeof(udp_server_add))) == -1) {
                        	printf("Something wrong with socket sending packet\n");
                        	exit(1);
                    	}
                    	
                    	initPacket(&localAnwserPacket,1);
                    	memset(udp_rec_buf, '\0', BUF_SIZE);
                    
                    	if ((recvfrom(sock, udp_rec_buf, BUF_SIZE, 0, (struct sockaddr *) &udp_server_add, &remAddrLen)) == -1) {
                        	printf("Something wrong with socket receving\n");
                    	}

                    	end = clock();
                    	decode_packet(&localAnwserPacket, &udp_rec_buf);
                    	
                    	if (localAnwserPacket.header->tag != name_wrong_res) {
                       	 printf("receive packet from %s\n", udp_server_ip_3);
                    	} 
						else {
                       		printf("receive packet from %s: no such domain name\n", udp_server_ip);
                    	}
                    	duration = (double) (end - start) / 1000;

                    	printf("time duration: %f seconds\n", duration);
	  					udp_rec_buf = h_udp_rec_buf; 
	       				memset(udp_rec_buf, 0, BUF_SIZE);
	       				jump_2 = 1;
					 }
					}
					
                if (localAnwserPacket.header->tag != name_wrong_res) {
                    if (localAnwserPacket.querySection->qType == 1) {
                        add_A_RR(localAnwserPacket, "./resource_records.txt");
                    } else if (localAnwserPacket.querySection->qType == 5) {
                        add_CNAME_RR(localAnwserPacket, "./resource_records.txt");
                    } else {
                        add_MX_RR(localAnwserPacket, "./resource_records.txt");
                    }
                }

                char *temp_buf = (char *) malloc(sizeof(char) * BUF_SIZE);
                memset(temp_buf, '\0', BUF_SIZE);
                char *send_buf = temp_buf;
                
                temp_buf += 2;
                encode_packet(&localAnwserPacket, &temp_buf);
                printf("send packet to %s\n", inet_ntoa(remote_addr.sin_addr));
                printf("********************************\n");
                printf("finish and add cache\n");
                printf("********************************\n");
                length = htons(length);
                memcpy(send_buf, &length, sizeof(length));
                length = ntohs(length);
                
                if (send(client_fd, send_buf, length + 2, 0) == -1) {
                    printf("Something wrong with socket sending packet\n");
                    exit(1);
                }
                length = 0;
                query_domain_length = 0;
                jump_1 = 0;
                jump_2 = 0;
        	}
            close(client_fd);
        }
        return 0;
      }

    void initPacket(struct packet *Packet, int flag) {

        Packet->header = (struct dnsHeader *) malloc(sizeof(struct dnsHeader));
        memset(Packet->header, 0, sizeof(struct dnsHeader));

        Packet->querySection = (struct dnsQuery *) malloc(sizeof(struct dnsQuery));
        memset(Packet->querySection, 0, sizeof(struct dnsQuery));

        Packet->querySection->qName = (char *) malloc(sizeof(char) * BUF_SIZE);
        memset(Packet->querySection->qName, '\0', BUF_SIZE);

        if (flag == 0) {//non-authority
            Packet->A_answerSection = NULL;
            Packet->A_authoritySection = NULL;
            Packet->A_additionalSection = NULL;

            Packet->MX_answerSection = NULL;
            Packet->MX_authoritySection = NULL;
            Packet->MX_additionalSection = NULL;

            Packet->C_answerSection = NULL;
            Packet->C_authoritySection = NULL;
            Packet->C_additionalSection = NULL;
        } else {//authority
            //A
            Packet->A_answerSection = (struct dnsRR_A *) malloc(sizeof(struct dnsRR_A));
            memset(Packet->A_answerSection, 0, sizeof(struct dnsRR_A));
            Packet->A_answerSection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->A_answerSection->dname, '\0', BUF_SIZE);
            //CNAME
            Packet->C_answerSection = (struct dnsRR_CNAME *) malloc(sizeof(struct dnsRR_CNAME));
            memset(Packet->C_answerSection, 0, sizeof(struct dnsRR_CNAME));
            Packet->C_answerSection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->C_answerSection->dname, '\0', BUF_SIZE);
            Packet->C_answerSection->rData = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->C_answerSection->rData, '\0', BUF_SIZE);
            //MX
            Packet->MX_answerSection = (struct dnsRR_MX *) malloc(sizeof(struct dnsRR_MX));
            memset(Packet->MX_answerSection, 0, sizeof(struct dnsRR_MX));
            Packet->MX_answerSection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->MX_answerSection->dname, '\0', BUF_SIZE);
            Packet->MX_answerSection->rData = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->MX_answerSection->rData, '\0', BUF_SIZE);

            //A
            Packet->A_authoritySection = (struct dnsRR_A *) malloc(sizeof(struct dnsRR_A));
            memset(Packet->A_authoritySection, 0, sizeof(struct dnsRR_A));
            Packet->A_authoritySection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->A_authoritySection->dname, '\0', BUF_SIZE);
            //CNAME
            Packet->C_authoritySection = (struct dnsRR_CNAME *) malloc(sizeof(struct dnsRR_CNAME));
            memset(Packet->C_authoritySection, 0, sizeof(struct dnsRR_CNAME));
            Packet->C_authoritySection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->C_authoritySection->dname, '\0', BUF_SIZE);
            Packet->C_authoritySection->rData = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->C_authoritySection->rData, '\0', BUF_SIZE);
            //MX
            Packet->MX_authoritySection = (struct dnsRR_MX *) malloc(sizeof(struct dnsRR_MX));
            memset(Packet->MX_authoritySection, 0, sizeof(struct dnsRR_MX));
            Packet->MX_authoritySection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->MX_authoritySection->dname, '\0', BUF_SIZE);
            Packet->MX_authoritySection->rData = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->MX_authoritySection->rData, '\0', BUF_SIZE);

            //A
            Packet->A_additionalSection = (struct dnsRR_A *) malloc(sizeof(struct dnsRR_A));
            memset(Packet->A_additionalSection, 0, sizeof(struct dnsRR_A));
            Packet->A_additionalSection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->A_additionalSection->dname, '\0', BUF_SIZE);
            //CNAME
            Packet->C_additionalSection = (struct dnsRR_CNAME *) malloc(sizeof(struct dnsRR_CNAME));
            memset(Packet->C_additionalSection, 0, sizeof(struct dnsRR_CNAME));
            Packet->C_additionalSection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->C_additionalSection->dname, '\0', BUF_SIZE);
            Packet->C_additionalSection->rData = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->C_additionalSection->rData, '\0', BUF_SIZE);
            //MX
            Packet->MX_additionalSection = (struct dnsRR_MX *) malloc(sizeof(struct dnsRR_MX));
            memset(Packet->MX_additionalSection, 0, sizeof(struct dnsRR_MX));
            Packet->MX_additionalSection->dname = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->MX_additionalSection->dname, '\0', BUF_SIZE);
            Packet->MX_additionalSection->rData = (char *) malloc(sizeof(char) * BUF_SIZE);
            memset(Packet->MX_additionalSection->rData, '\0', BUF_SIZE);
        }
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
			char_encode_resource_records(packet, buffer, 1);
		}
		else{
			char_encode_resource_records(packet, buffer, 1);
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
	    	memcpy(*buffer, &packet->A_authoritySection->rData, 4);
	  		*buffer += 4;
	  		length += 4;
		}
		else{
			if(packet->querySection->qType == 15){
				encode_domain_name(buffer, packet->A_additionalSection->dname);
	    		put16bits(buffer, packet->A_additionalSection->type);
	    		put16bits(buffer, packet->A_additionalSection->_class);
	    		put32bits(buffer, packet->A_additionalSection->ttl);
	    		put16bits(buffer, packet->A_additionalSection->rDataLen);
	    		memcpy(*buffer, &packet->A_additionalSection->rData, 4);
	  			*buffer += 4;
	  			length += 4;
				return;
			}
			encode_domain_name(buffer, packet->A_additionalSection->dname);
	    	put16bits(buffer, packet->A_additionalSection->type);
	    	put16bits(buffer, packet->A_additionalSection->_class);
	    	put32bits(buffer, packet->A_additionalSection->ttl);
	    	put16bits(buffer, packet->A_additionalSection->rDataLen);
	    	memcpy(*buffer, &packet->A_additionalSection->rData, 4);
	  		*buffer += 4;
	  		length += 4;
		}
	}
	void char_encode_resource_records(struct packet* packet, char** buffer, int flag) {
		
	  	if(flag == 0){
	  		if(packet->querySection->qType == 15){
	  			unsigned short pre = 5;
	  			encode_domain_name(buffer, packet->MX_answerSection->dname);
	    		put16bits(buffer, packet->MX_answerSection->type);
	    		put16bits(buffer, packet->MX_answerSection->_class);
	    		put32bits(buffer, packet->MX_answerSection->ttl);
	    		put16bits(buffer, packet->MX_answerSection->rDataLen);
	    		put16bits(buffer, pre);
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
				unsigned short pre = 5;
				encode_domain_name(buffer, packet->MX_authoritySection->dname);
	    		put16bits(buffer, packet->MX_authoritySection->type);
	    		put16bits(buffer, packet->MX_authoritySection->_class);
	    		put32bits(buffer, packet->MX_authoritySection->ttl);
	    		put16bits(buffer, packet->MX_authoritySection->rDataLen);
	    		put16bits(buffer, pre);
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
    void decode_IP(struct packet* packet, char** buffer){
    	decode_header(packet->header, buffer);
	  	packet->querySection->qName = decode_domain_name(buffer);
	  	packet->querySection->qType = get16bits(buffer);
	  	packet->querySection->qClass = get16bits(buffer);
	  	int_decode_resource_records(packet, buffer, 1);//解析IP 
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
	  	printf_IP = inet_ntoa(ip);//打印得到的IP地址 
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
	  	
//	  	struct in_addr ip;
//	  	memset(&ip,0,sizeof(struct in_addr));
//	  	ip.s_addr = value;
//	  	printf_IP = inet_ntoa(ip);
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
//	  	if(packet->querySection->qType == 15){
//	  		packet->MX_additionalSection->dname = decode_domain_name(buffer);
//	  		packet->MX_additionalSection->type = get16bits(buffer);
//	  		packet->MX_additionalSection->_class = get16bits(buffer);
//	 		packet->MX_additionalSection->ttl = get32bits(buffer);
//	  		packet->MX_additionalSection->rDataLen = get16bits(buffer);
//	  		*buffer+=2;//preference
//	  		packet->MX_authoritySection->rData = decode_domain_name(buffer);
//	  		printf_Name = packet->MX_authoritySection->rData;
//	  		return;
//		  }
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
	struct list* getRR (int flag, char* fpath,char* qName, unsigned short qType) {
	
	  char* buf = (char *)malloc(sizeof(char) * BUF_SIZE);
	  memset(buf, '\0', BUF_SIZE); 
	  FILE * fp = fopen(fpath, "a+");
	  
	  struct list* returnList = (struct list*)malloc(sizeof(struct list));
	  
	  if (fp == NULL) {
	    printf("Can not read the resource record.");
	    exit(0);
	  }
	  while (fgets(buf, BUFSIZ, fp) != NULL) {
	    int len = strlen(buf);//不计算结束字符‘\0’进去 
	    buf[len-1] = '\0';//最后一位为换行符,所以要变成结束符号 
	    len = strlen(buf);
	    //读取顶级域名 
	    
	    int i = 0;
	    char* name = (char *)malloc(sizeof(char) * BUF_SIZE);
	    memset(name, '\0', BUF_SIZE);
	    for (i; i < len; i++) {
	      if (buf[i] == '#') {
	        memcpy(name, buf, i);//com org us cn
	        buf = buf + i + 1;
	        i=0;
	        break;
	      }
	    }
	    len = strlen(buf);
	    char* temp_type = (char *)malloc(sizeof(char) * BUF_SIZE);
	    memset(temp_type, '\0', BUF_SIZE);
	    int type = 0;
	  	//读取类型 
	    for (i; i < len; i++) {
	      if (buf[i] == '#') {
	      	memcpy(temp_type, buf, i);
	        type = getType(temp_type);
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
	    if (flag == 0) {
	      struct  dnsRR_A* p = (struct dnsRR_A*)malloc(sizeof(struct dnsRR_A));
	  	  memset(p,0,sizeof(struct dnsRR_A));
	  	  p->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
	      memset(p->dname, '\0', BUF_SIZE);
	      
	      p->dname = name;
	      p->type = type;
	      p->_class = 0x1;
	      p->ttl = 100;
	      parse_A_rData(p, buf);
	      returnList->A = p;
	      returnList->C = NULL;
	      returnList->M = NULL;
	      if(existRR(qName,qType,p->dname,p->type,0)){
	      	mark = 1;
	    	return returnList;
		  }
		  else{
		  	memset(p, 0, sizeof(struct dnsRR_A *));
			memset(returnList,0,sizeof(struct list));
		  }
	    } 
		else if (flag == 1) {
		  struct  dnsRR_CNAME* p = (struct dnsRR_CNAME*)malloc(sizeof(struct dnsRR_CNAME));
	  	  memset(p,0,sizeof(struct dnsRR_CNAME));
	  	  p->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
	      memset(p->dname, '\0', BUF_SIZE);
	      p->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
	      memset(p->rData, '\0', BUF_SIZE);
	      
	      p->dname = name;
	      p->type = type;
	      p->_class = 0x1;
	      p->ttl = 100;
	      
	      parse_CNAME_rData(p, buf);
	      returnList->C = p;
	      returnList->A = NULL;
	      returnList->M = NULL;
	      if(existRR(qName,qType,p->dname,p->type,1)){
	      	mark = 2;
	    	return returnList;
		  }
		  else{
		  	memset(p, 0, sizeof(struct dnsRR_CNAME *));
			memset(returnList,0,sizeof(struct list));
		  }
	    } 
		else {
		  struct  dnsRR_MX* p = (struct dnsRR_MX*)malloc(sizeof(struct dnsRR_MX));
	  	  memset(p,0,sizeof(struct dnsRR_MX));
	  	  p->dname = (char *)malloc(sizeof(char) * BUF_SIZE);
	      memset(p->dname, '\0', BUF_SIZE);
	      p->rData = (char *)malloc(sizeof(char) * BUF_SIZE);
	      memset(p->rData, '\0', BUF_SIZE);
	      
	      p->dname = name;
	      p->type = type;
	      p->_class = 0x1;
	      p->ttl = 100;
	      parse_MX_rData(p, buf, prfrc);
	      
	      returnList->M =p;
	      returnList->A = NULL;
	      returnList->C = NULL;
	      if(existRR(qName,qType,p->dname,p->type,2)){
	      	mark = 3;
	    	return returnList;
		  }
		  else{
		  	memset(p, 0, sizeof(struct dnsRR_MX *));
			memset(returnList,0,sizeof(struct list));
		  }
	    }
	  }
	  fclose(fp);
	  
	  printf("Records don't exist\n");
	  returnList->A = NULL;
	  returnList->C = NULL;
	  returnList->M = NULL;
	  return returnList;
	}
	int existRR(char* qName, unsigned short qType, char* name, unsigned short type, int flag){
		
		if(flag == 0){
			if(type == qType && !strcmp(qName, name)) {
      		return 1;
    	    }
    	else{
    		return 0;
		    }
		}
		else if(flag == 1){
			if(type == qType && !strcmp(qName, name)) {
      		return 1;
    	    }
    	else{
    		return 0;
		    }
		}
		else{
			if(type == qType && !strcmp(qName, name)) {
      		return 1;
    	    }
    	else{
    		return 0;
		    }
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
	void parse_CNAME_rData(struct dnsRR_CNAME* p, char* buffer) {
	  
	  int i = 0;
	  while(buffer[i] != '\0'){
	  	if(buffer[i] == '!'){
	  		memcpy(p->rData, buffer, i);
	  		*buffer += i;
	  		p->rDataLen = i;
		  }
		i++;
	  }
	}
	void parse_MX_rData(struct dnsRR_MX* p, char* buffer, unsigned short prfrc) {
	  
	  int i = 0;
	  while(buffer[i] != '\0'){
	  	if(buffer[i] == '!'){
	  		memcpy(p->rData, buffer, i);
	  		*buffer += i;
	  		p->rDataLen = i;
		  }
		i++;
	  }
	}
    unsigned short getType(char *type) {
        enum RR_type type_code;
        if (!strcmp(type, "A")) {
            type_code = A;
            return type_code;
        }
        else if (!strcmp(type, "MX"))
        {
            type_code = MX;
            return type_code;
        } else if (!strcmp(type, "CNAME")) {
            type_code = CNAME;
            return type_code;
        } else {
            printf("No such query type, [A | MX | CNAME] is considered\n");
            exit(0);
        }
    }
	
    void add_A_RR (struct packet packet, char* fpath) {
        FILE * fp = fopen(fpath, "a");
        if (fp == NULL) {
            printf("can not add the resource record to cache");
            exit(0);
        }
        fwrite(packet.querySection->qName, strlen(packet.querySection->qName), 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("A", 1, 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("0", 1, 1, fp);
        fwrite("#", 1, 1, fp);
        
		char* data = (char *)malloc(sizeof(char) * BUF_SIZE);
		unsigned int len = 0;
		memset(data, '\0', BUF_SIZE);
		struct in_addr ip_store;
	  	memset(&ip_store,0,sizeof(struct in_addr));
	  	ip_store.s_addr = packet.A_authoritySection->rData;
	  	data = inet_ntoa(ip_store);
	  	len = strlen(data);
	  	
        fwrite(data, len, 1, fp);
        fwrite("!", 1, 1, fp);
        fwrite("\n", 1, 1, fp);
        fclose(fp);
    }
    void add_CNAME_RR (struct packet packet, char* fpath) {
        FILE * fp = fopen(fpath, "a");
        if (fp == NULL) {
            printf("can not add the resource record to cache");
            exit(0);
        }
        fwrite(packet.querySection->qName, strlen(packet.querySection->qName), 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("CNAME", 5, 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("0", 1, 1, fp);
        fwrite("#", 1, 1, fp);
        
        char* data = (char *)malloc(sizeof(char) * BUF_SIZE);
        memset(data, '\0', BUF_SIZE);
        data = packet.C_authoritySection->rData;
        fwrite(data, strlen(data), 1, fp);
        fwrite("!", 1, 1, fp);
        fwrite("\n", 1, 1, fp);
        fclose(fp);
    }
    void add_MX_RR (struct packet packet, char* fpath) {
    	unsigned int len = 0;
        FILE * fp = fopen(fpath, "a");
        if (fp == NULL) {
            printf("can not add the resource record to cache");
            exit(0);
        }
        
        fwrite(packet.querySection->qName, strlen(packet.querySection->qName), 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("MX", 2, 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("5", 1, 1, fp);
        fwrite("#", 1, 1, fp);
        
        char* data = (char *)malloc(sizeof(char) * BUF_SIZE);
        memset(data, '\0', BUF_SIZE);
        
        memcpy(data, packet.MX_answerSection->rData, sizeof(packet.MX_answerSection->rDataLen-2));
        
        fwrite(data, strlen(data), 1, fp);
        fwrite("!", 1, 1, fp);
        fwrite("\n", 1, 1, fp);

        fwrite(data, strlen(data), 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("MX", 2, 1, fp);
        fwrite("#", 1, 1, fp);
        fwrite("5", 1, 1, fp);
        fwrite("#", 1, 1, fp);
        
        memset(data, '\0', BUF_SIZE);
		struct in_addr ip_store;
	  	memset(&ip_store,0,sizeof(struct in_addr));
	  	ip_store.s_addr = packet.A_additionalSection->rData;
	  	data = inet_ntoa(ip_store);
	  	len = strlen(data);
	  	
        fwrite(data, len, 1, fp);
        fwrite("!", 1, 1, fp);
        fwrite("\n", 1, 1, fp);
    
        fclose(fp);
    }

    void printPacket(struct packet packet) {
        
    }

