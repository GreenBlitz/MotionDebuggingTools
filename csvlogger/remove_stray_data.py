import sys
from numbers import Real

def main():
    assert len(sys.argv) > 2
    delete_error = sys.argv[2]
    assert delete_error.isnumeric()
    filename = sys.argv[1]


    lines = open(filename, "r").read().split("\n")
    print(lines)
    with open(filename, "w") as f:
        l = ""
        for line in lines[1:]:
            print(line)
            a = line.split(",")
            if abs(float(a[-1])) < float(delete_error) and line != "\n":
                l += "\n" + line

        f.write(lines[0] + l)

if __name__ == "__main__":
    main()