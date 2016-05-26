from socket import *
import sys
import os
import time  

#
def printGrid(arr,n):												#print the grid with "X and "@" move
	i=1
	while(i<=len(arr)):
		if ((i%n)!=0):
			if (arr[str(i)]==1):
				print "X"+" | ",
				i = i+1
			elif(arr[str(i)]== -1):
				print "@"+" | ",
				i=i+1
			else:
				print str(i).zfill(2)+" | ",
				i=i+1
		else:
			if (arr[str(i)]==1):
				print "X"
				i = i+1
			elif(arr[str(i)]== -1):
				print "@"
				i=i+1
			else:
				print str(i).zfill(2)
				i=i+1
			j=1
			while (j<=n):
				print "_____",
				j= j+1
			print ""
def initializedict(size):											#intialize dictionary	
	arr = {}
	i = 1
	while (i<=size*size):
		arr[str(i)] = 0
		i=i+1
	return arr
def update_score(score,grid_size,message,flag):						#update the score
	row = (int(message)-1)/grid_size
	col = (int(message)-1)%grid_size
	score[row] = score[row]+flag
	score[grid_size+col] = score[grid_size+col]+flag
	if (row==col):
		score[2*grid_size] = score[2*grid_size]+flag
	if (row==(grid_size-1-col)):
		score[2*grid_size+1] = score[2*grid_size+1]+flag

def checkdict(arr,size):											#check dictionary
	i=1
	while (i<=size*size):
		if (arr[str(i)]==0):
			return 1
		else:
			i=i+1
	return 0

def init_score(size):												#intialize scorecard
	i=0
	arr = []
	while (i<((2* size)+2)):
		arr.append(0)
		i=i+1
	return arr
def check_recv(message,connection):									#check data(message,move,pause,quit) while receive
	if (message == 'P'):
		while True:
			print "Other Player paused the Game "
			message = connection.recv(1024)
			if(message == 'R'):
				print "Game resume , Waiting for Other's Move........"
				message = connection.recv(1024)
				return check_recv(message,connection)
				break
			elif(message == 'Q'):
				print "Other player Quit the Game !"
				print "You won this Game"
				printscore(store,grid_size)
				sys.exit()
	elif(message == 'Q'):
		sys.exit()	
	elif(message[0] == '@'):
		while True:
			print "<Other> ",
			message = message.replace(message[0]," ")
			print message
			message = connection.recv(1024)
			return check_recv(message,connection)
			break
	else:
		return message

def check_send(message,connection,grid_size):					#check data(message,move,pause,quit) while sending
	if (message == 'P'):
		while True:
			message = raw_input("Press R to Resume")
			connection.sendall(message)
			if(message == 'R'):
				message = raw_input("Your move(1-" + str(grid_size*grid_size) + ") ")
				connection.sendall(message)
				return check_send(message,connection,grid_size)
				break
			elif(message == 'Q'):
				sys.exit()
	elif(message == 'Q'):
		sys.exit()
	elif(message[0] == '@'):
		print "<You> ",
		message = message.replace(message[0],"")
		print message
		message = raw_input("Your move(1-" + str(grid_size*grid_size) + ") ")
		connection.sendall(message)
		return check_send(message,connection,grid_size)
	else:
		return message

def checkwin(arr,size):											#check whether any player wins or not
	i=0
	while (i<len(arr)):
		if (arr[i]==size):
			return 1
		elif(arr[i]==((-1)*size)):
			return -1
		else:
			i=i+1
	return 0

def printscore(score,size):										#print the final tournament score
	i=0
	while (i<len(score)):
		print "Match " + str(i+1) +" screenshot ="
		printGrid(score[i],size)
		print ""
		i=i+1

def returnport():														#return port
	return 12569

HOST = '0.0.0.0' 														#Hosting the Game				
PORT = returnport()
s = socket(AF_INET, SOCK_STREAM)										# 98% of all socket programming will use AF_INET and SOCK_STREAM
s.bind((HOST, PORT)) 
s.listen(10)

f = open("gameplay.txt","r")											#open tutorial for game play
ch = raw_input("tutorial for game.............press y")
if (ch=="y"):
	print(f.read())
	while True:
		con = raw_input("Press Enter to continue.............")
		if(con == ""):
			break

os.system("clear")														#waiting for connection	
print "Waiting for ohter player to Connect .........."

conn, addr = s.accept() # accept the connection
os.system("clear")
while True:
	grid_size = int(raw_input("Enter Grid Size = "))					#grid size
	if(grid_size < 2):
		continue
	else:
		break
while True:			 									
	num_game = int(raw_input("Enter number of Games you want to play(>0) = ")) #number of games
	if (num_game < 1):
		continue
	else:
		break
conn.sendall(str(grid_size).zfill(4) + str(num_game).zfill(4))
tournament = {'1':0,'2':0}
store = []
os.system("clear") 

i=1
while (i<=num_game):
	arr= initializedict(grid_size)
	score = init_score(grid_size)
	printGrid(arr,grid_size) 
	print ""
	while True:
		if(checkdict(arr,grid_size)==1):            			#checking the number of games
			print "Wait for Player1's Move"
			while True:
				data = conn.recv(1024)							#waiting for other's move
				data = check_recv(data,conn)
				if(int(data) > (grid_size*grid_size)):
					continue
				if(int(data) < 1):          #checking the valid move
					continue
				elif(arr[data]==0):
					arr[data] = -1
					break;
				elif(arr[data]!=0):
					continue	
				else:
					break				
			arr[data] = -1
			update_score(score,grid_size,data,-1)            	#update the scorecard 
			flag_win = checkwin(score,grid_size)				#check whether any player wins or not
			os.system("clear")
			printGrid(arr,grid_size)							#print the grid with updated score
			print ""
			sys.stdout.flush()
			if (flag_win==-1):									
				print "You LOSE !!!!"
				tournament[str(1)] = tournament[str(1)]+1 
				break
			elif(flag_win==1):
				print "You WON !!!!"
				tournament[str(2)] = tournament[str(2)]+1 
				break
		else:
			break

		if(checkdict(arr,grid_size)==1):
			while True:
				reply = raw_input("Your move(1-" + str(grid_size*grid_size) + ") ")
				conn.sendall(reply)										#send data to other player
				reply =check_send(reply,conn,grid_size)					#check data (message,move,Pause,Quit) 
				if(int(reply) > (grid_size*grid_size)):
					print "Move Out Of The Range.......Enter Again.."
				if(int(reply) < 1):
					print "Move Out Of The Range.......Enter Again.."
				elif(arr[reply]==0):
					arr[reply] = 1
					break;
				else:
					print "Wrong Move.....Enter Again.."
			update_score(score,grid_size,reply,1)					#update the scorecard
			flag_win = checkwin(score,grid_size)					#check whether any player wins or not
			os.system("clear") 										#clear screen
			printGrid(arr,grid_size)								#print updated score
			print ""
			if (flag_win==-1):
				print "You LOSE !!!!"
				tournament[str(1)] = tournament[str(1)]+1 
				break
			elif(flag_win==1):
				print "You WON !!!!"
				tournament[str(2)] = tournament[str(2)]+1 
				break
		else:
			break
	print tournament["1"],											#print the tournament score
	print " : ",
	print tournament["2"]
	store.append(arr)												
	i=i+1
	time.sleep(3)													#sleep for 3 sec
	os.system("clear") # after each game
os.system("clear")
print "Final Score of Tournament "									#final scorecard	
print ""
print "Player 1 : Player 2 = ",
print tournament["1"],
print " : ",
print tournament["2"]
printscore(store,grid_size)											#print screenshots
conn.close()														#close the socket