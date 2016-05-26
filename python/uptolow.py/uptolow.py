from pip._vendor.distlib.compat import raw_input

S = raw_input()
i=0
S = list(S)
print(S)
while (i<len(S)):
    if(int(S[i]) <=90):
        if(int(S[i]) >=65):
            S[i] = int(S[i])+97-65
            i = i+1
            continue
    elif(int(S[i]) <=122):
        if(int(S[i]) >=97):
            S[i] = int(S[i])-97+65
            i = i+1
            continue
print(S)