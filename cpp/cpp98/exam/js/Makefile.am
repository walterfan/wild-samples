AUTOMAKE_OPTIONS = foreign
AM_CPPFLAGS=-I$(top_srcdir)/util -I/usr/local/include

LIBEVENT_DIR=/usr/local/Cellar/libevent/2.1.8

noinst_PROGRAMS = SpiderMonkeyTest V8Test

SpiderMonkeyTest_SOURCES = SpiderMonkeyTest.cpp 
SpiderMonkeyTest_CPPFLAGS = -I$(MOZJS_INC_DIR) -std=c++11
SpiderMonkeyTest_LDFLAGS=-L/usr/local/lib/ 
SpiderMonkeyTest_LDADD = -lmozjs-31

V8Test_SOURCES = v8test.cpp 
V8Test_CPPFLAGS = -I$(top_srcdir)/third_party/v8 -I$(top_srcdir)/util -pthread -lrt -stdlib=libstdc++ -std=c++11
V8Test_LDFLAGS =  -L/workspace/cpp/v8/xcodebuild/Release -L$(top_srcdir)/util -stdlib=libstdc++ -std=c++11
V8Test_LDADD = -lv8_base -lv8_libbase -lv8_snapshot -lv8_libplatform -licuuc -licui18n -licudata  -ltinyutil