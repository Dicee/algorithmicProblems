nb_customers = int(input().split(" ")[1])
prices       = [ int(x) for x in input().split(" ") ]
prices.sort()

(nth_purchase, cost) = (0, 0)
while len(prices) != 0:
    for i in range(min(len(prices), nb_customers)):
        cost += prices.pop() * (1 + nth_purchase)
    nth_purchase += 1

print(cost)