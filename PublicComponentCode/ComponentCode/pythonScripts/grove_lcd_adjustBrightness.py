from grove_rgb_lcd import *

def adjust(brightness):
    i = int(brightness)
    if i >= 16:
        i = 15
    b = i * 16      # set 16 level of brightness (0 : off , 15 : brightest)

    setRGB(b,b,b)

adjust(sys.argv[1])
