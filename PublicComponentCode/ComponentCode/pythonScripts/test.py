def hello():
    f = open("demofile.txt", "a")
    f.write("Now the file has one more line!") 
    return "hello"
def message(message, newMessage):
    f = open("demofile.txt", "a")
    f.write(message + newMessage) 
    return "wrote to file"