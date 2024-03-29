/*---------------------------------------------------------
*
* Description:
*    we can put test data to lua script with keyword and tag
*    then get the test data by keyword and tag(case name
*
*       keyword=".."
*       keyword=[[...]]
*    or
*       CaseName.keyword = "..."
*       CaseName.keyword = [[
*       ....
*       ]]
*
* Version:1.0
* History:
*   created on 04/25/2013
*
-----------------------------------------------------------*/
#ifndef _TINY_UTIL_H__
#define _TINY_UTIL_H__

#include <string>

#define LUA_ENUM(L, name, val) \
  lua_pushlstring(L, #name, sizeof(#name)-1); \
  lua_pushnumber(L, val); \
  lua_settable(L, -3);


extern int String2File(std::string str, std::string filename);
extern int File2String(std::string filename, std::string& str);

struct lua_State;

class CLuaUtil
{
public:
    CLuaUtil();

    CLuaUtil(std::string testfile);

    ~CLuaUtil();

    std::string GetString(std::string strKeyword, std::string strCaseName = "");

    int GetNumber(std::string strKeyword, std::string strCaseName = "");

    int ExecuteLuaFile(std::string strFileName);

    int ExecuteLuaCodes(std::string strLuaCodes);

	lua_State* GetLuaState() {return m_pState;}	

    static std::string GetLuaPath() ;

    static int LoadLuaFile(lua_State *pState, const char* pFileName);

    void Reset();
    //void Dump();
private:
    std::string ReadTestData(std::string strKeyword, std::string strCaseName = "");
    void Init();
    void Clean();

    lua_State* m_pState;
    std::string m_strLuaPath;
    bool m_bInited;

};


#endif
