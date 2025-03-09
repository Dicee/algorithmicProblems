class ReverseInteger {
    public int reverse(int x) {
        int y = 0;
        while (x != 0) {
            int lastDigit = x % 10;
            x /= 10;

            if (y < 0 && y < (Integer.MIN_VALUE - lastDigit) / 10) {
                return 0;
            } else if (y > 0 && y > (Integer.MAX_VALUE - lastDigit) / 10) {
                return 0;
            }

            y = 10 * y + lastDigit;
        }
        return y;
    }
}
