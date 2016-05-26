#include <iostream>
using namespace std;

struct Node
  {
     int data;
     Node *next;
  };

void InsertNth(Node *head, int data, int position)
{
	cout << "hello5" <<endl;
    Node * ptr = head;
    cout << "hello" <<endl;
    int i=0;
    while (i<position)
        {
        i++;
        ptr = ptr->next;
    }
    Node * temp = new Node;
    cout << "hello4" <<endl;
    temp->data = data;
    cout << "hello3" <<endl;
    temp->next = ptr->next;
    cout << "hello2" <<endl;
    ptr->next = temp;
    cout << "hello1" <<endl;
}
void Print(Node *head)
{
     while(1)
        {
        if(head!=NULL)
        {
        cout << head->data<< endl ;
        head = head->next;
    }
        else
            break;
    }
}
int main()
{
    int x,y;
    cout << "hello1" <<endl;
    Node * head = new Node;
    cout << "hello2" <<endl;
    head =NULL;
    cout << "hello3" <<endl;
    for(int i=0;i<6;i++)
    {
        // cin >>x>>y;
        cout << "hello4" <<endl;
        InsertNth(head,x,y);
        Print(head);
    }
}
