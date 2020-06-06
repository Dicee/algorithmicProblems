import sys
with open(sys.argv[1], 'r') as lines:
    for line in lines:
        numbers = [ int(ch) for ch in line.replace(' ', '').strip() ]
        sum     = 0
        for i, x in enumerate(numbers):
            sum += 2 * x if i % 2 == 0 else x
        print("Real" if sum % 10 == 0 else "Fake")