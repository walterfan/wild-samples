#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <iostream>

using namespace std;

class User 
{
public:
	User(string id, string name): m_id(id), m_name(name) {}
	~User() {}
	string& GetId()  { return m_id;}
	string& GetName() { return m_name;}


	string m_id;
	string m_name;


};

int main(int argc, char** argv) 
{

	User* pUser = new User("1", "walter");
	string& name = pUser->GetName();
	User& aUser = *pUser;
	delete pUser;
	pUser = NULL;

	cout<<name<<endl;
	cout<<aUser.GetName()<<endl;

	return 0;

}
