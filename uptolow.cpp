#include <iostream>
using namespace std;

int main()
{
    string S;
    getline(cin,S);
    int i=0;
    while (i<S.length())
    {
        if((int)S[i] <=90 && (int)S[i] >=65)
        {
            S[i] = (int)S[i]+97-65;
        }
        else if((int)S[i] <=122 && (int)S[i] >=97)
        {
            S[i] = (int)S[i]-97+65;
        }
        i++;
    }
    cout << S<<endl;
}
