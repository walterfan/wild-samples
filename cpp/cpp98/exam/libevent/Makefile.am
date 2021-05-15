AUTOMAKE_OPTIONS = foreign
AM_CPPFLAGS=-I$(top_srcdir)/util -I/usr/local/include

LIBEVENT_DIR=/usr/local/Cellar/libevent/2.1.8

noinst_PROGRAMS = rot13fork rot13select rot13event1 rot13event2

rot13fork_SOURCES = rot13_fork_1st.cpp
rot13select_SOURCES = rot13_select_1st.cpp
rot13event1_SOURCES = rot13_libevent_1st.cpp
rot13event2_SOURCES = rot13_libevent_2nd.cpp

rot13event1_LDFLAGS = -L$(LIBEVENT_DIR)/lib
rot13event2_LDFLAGS = -L$(LIBEVENT_DIR)/lib

rot13event1_LDADD = -levent
rot13event2_LDADD = -levent

rot13event1_CPPFLAGS= -I$(LIBEVENT_DIR)/include
rot13event2_CPPFLAGS= -I$(LIBEVENT_DIR)/include