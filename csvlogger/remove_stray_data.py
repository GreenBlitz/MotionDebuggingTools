import sys
from numbers import Real

def main():
    assert len(sys.argv) > 2
    delete_error = sys.argv[-1]
    assert delete_error.isnumeric()

    for i in sys.argv[1:-1]:
        remove_stray(i)

def remove_stray(filename):
    filename = sys.argv[1]


    lines = open(filename, "r").read().split("\n")
    with open(filename, "w") as f:
        l = ""
        for line in lines[1:-1]:
            a = line.split(",")
            try:
                if abs(float(a[-1])) < float(delete_error) and line != "\n":
                    l += "\n" + line
            except:
                print(line)

        f.write(lines[0] + l)

if __name__ == "__main__":
    main()