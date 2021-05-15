noinst_PROGRAMS = http_client http_server

http_client_SOURCES = http_client_1st.cpp

http_server_SOURCES = http_server_1st.cpp
http_server_LDADD = -levent
http_server_CPPFLAGS= -I$(LIBEVENT_DIR) -I$(LIBEVENT_DIR)/include
http_server_LDFLAGS =-L$(LIBEVENT_DIR)/lib
