from grove_rgb_lcd import *

def grove_lcd(onOrOff):

  
  try:
    if(onOrOff == "ON"):
      setRGB(0,128,64)
      setText("")
      print("ON")
    if(onOrOff == "OFF"):
        setRGB(0,0,0)
        setText("")
        print("OFF")
  except IOError:
    print("ERROR")

grove_lcd(sys.argv[1])