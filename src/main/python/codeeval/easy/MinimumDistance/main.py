import sys
with open(sys.argv[1], 'r') as lines:
    for line in lines:
        split        = [ int(number) for number in line.split() ]
        (n, numbers) = (split[0], split[1:])
        # n < 100, this it is always cheap to sort the numbers. Otherwise, I would
        # have used quick-select
        numbers.sort()
        median = (numbers[n // 2 - 1] + numbers[n // 2 + 1]) // 2 if n % 2 == 0 else numbers[(n - 1) // 2]
        print(sum([ abs(number - median) for number in numbers ]))