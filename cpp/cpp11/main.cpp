#include <iostream>
#include <vector>
#include <algorithm>
#include <stdio.h>
#include <string>

using namespace std;

bool is_number(const std::string& s)
{
    return !s.empty() && std::find_if(s.begin(), 
        s.end(), [](char c) { return !std::isdigit(c); }) == s.end();
}

class PointSequence{
public:
    PointSequence(initializer_list<double> args) {
        if(args.size() %2 != 0) {
            throw invalid_argument("arguments count is wrong");    
        }

        for(auto it= args.begin();it!=args.end();++it) {
            m_vecPoints.push_back(*it);
        }
    }

    PointSequence(const PointSequence& rhs) {
        //TODO
    }

    PointSequence(const PointSequence&& rhs) {
        //TODO
    }

    void dump() {
        for(auto it= m_vecPoints.begin();it!=m_vecPoints.end();++it) {
            cout<<*it<<endl;
        }
    }
private:
    vector<double> m_vecPoints;
};



class SpeadSheetCell {
public:
    SpeadSheetCell() = default;

    SpeadSheetCell(double a) {
        m_dVal = a;
        m_strVal = to_string(a);
    }

    //todo: use delegate  
    SpeadSheetCell(string a) {
        m_strVal = a;
        if(is_number(a))
            m_dVal = stod(a);
        cout<<" SpeadSheetCell construct "<< m_strVal<<endl;
    }

    ~SpeadSheetCell() {
        cout<<" SpeadSheetCell deconstruct " << m_strVal<<endl;
    }

    SpeadSheetCell(const SpeadSheetCell& cell) {
        m_strVal = cell.getValue();
    }

    SpeadSheetCell& operator=(const SpeadSheetCell& cell) {
        m_strVal = cell.getValue();
        return *this;
    }

    //SpeadSheetCell() = delete;
    void setValue(string val) {
        m_strVal = val;
    }

    string getValue() const {
        return m_strVal;
    }
private:
    double m_dVal = 0;
    string m_strVal;
};

class SpeadSheet {
public:
    SpeadSheet(int width, int height):m_nWidth(width), m_nHeight(height) {
        m_cells = new SpeadSheetCell*[width];
        //TODO: create the 2nd level array
    }   
    ~SpeadSheet() {

    } 
private:
    int m_nWidth;
    int m_nHeight;
    SpeadSheetCell** m_cells;

    //vector<vector<make_unique_ptr<SpeadSheetCell>>>
};
void testLambda() {
     cout<< "cpp 11 testLambda" <<endl;
    int x = 0;
    int y = 3;

    auto ff = [=,&y]() mutable
    {
        cout << "x: " << x <<endl;
        cout << "y: " << y <<endl;
        y++;
    };

    x = 7;
    y = 7;
    ff();
    ff();

    vector<int> vec(10);
    int idx = 0;
    //generate .. [=]()

}

void testClass() {
    cout<< "cpp 11 testClass" <<endl;
    SpeadSheetCell cell;

    PointSequence p1 = {1.0, 2.0};
    p1.dump();

    try {
    PointSequence p1{1.0, 2.0, 3.0};
    } catch(const invalid_argument& e) {
        cerr<<"invalid_argument"<<endl;
    }

    auto aLambda = []{ cout << " aLambda testing" <<endl;};
    aLambda();

}

void swap(int&& a, int&& b) {
    int t = std::move(a);
    a = std::move(b);
    b = std::move(t);
}

void foo1(char*& arr) {

}
//&& --> rvalue reference
void foo2(int&& a) {
    //++a;
}

void testPtr() {
    cout<< "cpp 11 testPtr" <<endl;
    char* p = nullptr;
    //*p = 'a';

    char arr[10];
    foo1(p);

    int b=1;
    int c{};
    foo2(b+c);
   
    const int& a1 = 5;

    const char* str1 = "a";
    char* const str2 = "a";
    //**, *&

    int a2 = 3;
    int *pNum = &a2;
    int*& ptrRef = pNum;
    ptrRef = new int;
    *ptrRef = 5;
    //int&*, int&& not work


    std::unique_ptr<SpeadSheetCell> p0(new SpeadSheetCell("unique_ptr"));
    p0.release();

    std::shared_ptr<SpeadSheetCell> p1(new SpeadSheetCell("SpeadSheetCell"));


}

void testVec() {
    cout<< "cpp 11 testVec" <<endl;
    std::vector<int> v = {7, 5, 16, 8};

    v.push_back( 25 );
    v.push_back( 13 );

    auto it = v.cbegin();
    while ( it != v.cend() ) {
        std::cout << '\t' << *it;
        ++it;
    }
    std::cout << "\n";

    std::sort( v.begin(), v.end(), [](int x, int y){ return x<y; } );

    for( int n : v ) {
        std::cout << '\t' << n;
    }
    std::cout << "\n";

}


int main( void )
{
    cout<< "cpp 11 test" <<endl;
    testClass();
    testPtr();
    testVec();
    testLambda();
    //cout<< "exit?" <<endl;
    //getchar();

    return 0;
}
