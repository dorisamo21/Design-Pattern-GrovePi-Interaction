from grove_rgb_lcd import *
import time

def color(msg, color):
    try:
        if color == "Red":
            setRGB(255,0,0)

        if color == "Green":
            setRGB(0,255,0)

        if color == "Blue":
            setRGB(0,0,255)

        if color == "Yellow":
            setRGB(255,255,0)

        if color == "Magenta":
            setRGB(255,0,255)

        if color == "Cyan":
            setRGB(0,255,255)

        if color == "White":
            setRGB(255,255,255)

        if color == "Black":
            setRGB(0,0,0)

        if color == "Grey":
            setRGB(127,127,127)

        setText(msg.replace("_", " "))
        time.sleep(1)

    except KeyboardInterrupt:
        setText("KeyboardInterrupt")

    except IOError:
        setText("IOError")

color(sys.argv[1], sys.argv[2])
