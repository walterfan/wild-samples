#include <stdio.h>
#include <string.h>
#include <curl/curl.h>
#include <unistd.h>

const int URL_MAX_LEN = 2048;

int main(int argc, char* argv[])
{
  
  char url[URL_MAX_LEN] = "https://poros-a.wbx2.com/poros/api/v1/ping";
  if(argc > 1) {
    int len = strlen(argv[1]);
    if(len >= URL_MAX_LEN)
	 len = URL_MAX_LEN - 1;
    strncpy(url, argv[1], len);	
  }
  CURL *curl;
  CURLcode res;
 
  curl = curl_easy_init();
  if(curl) {
    curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
    curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);
    curl_easy_setopt(curl, CURLOPT_URL, url);
    curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
 
    for(int i=0; i<100; i++) {
      printf("\n%d. begin %ld \n", i, time(NULL));
      res = curl_easy_perform(curl);
      /* Check for errors */ 
      if(res != CURLE_OK)
        fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));

      printf("\n%d. end %ld \n", i, time(NULL));
      usleep(1000*1000*3);
    }
    /* always cleanup */ 
    curl_easy_cleanup(curl);
  }
  return 0;
}

