#include <iostream>

using namespace std;

const int n = 50;
int tally;
void total()
{
int count;
for (count = 1; count<= n; count++){
tally++;
}
}
int main()
{
tally = 0;
cout << tally <<endl;
return 0;
}
