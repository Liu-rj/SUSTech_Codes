#define _USE_MATH_DEFINES
#pragma GCC optimize(2)
#pragma GCC optimize("Ofast")

#include <bits/stdc++.h>

using namespace std;

void multiply(const int *a1, const int n1, const int *a2, const int n2, long long *res);

int lengthExtend(int max);

static int faster_streams = []() {
	srand(time(0));
	// use time to init the random seed
	std::ios::sync_with_stdio(false);
	std::istream::sync_with_stdio(false);
	std::ostream::sync_with_stdio(false);
	std::cin.tie(nullptr);
	std::cout.tie(nullptr);
	return 0;
}();

int main()
{
    int n;
    cin >> n;
    int *arr = new int[n];
    int maxn;
    for (int i = 0; i < n; i++)
    {
        cin >> arr[i];
    }
    sort(arr, arr + n);
    maxn = lengthExtend(arr[n - 1] + 1);
    int *a = new int[maxn]{};
    for (int i = 0; i < n; i++)
    {
        a[arr[i]] += 1;
    }
    int reslen = 2 * maxn;
    auto *out = new long long[reslen];
    multiply(a, maxn, a, maxn, out);
    delete[] a;
    for (int i = 0; i < n; i++)
    {
        out[2 * arr[i]]--;
    }
    for (int i = 0; i < reslen; i++)
    {
        out[i] /= 2;
    }
    long long *sum = new long long[reslen];
    sum[0] = out[0];
    for (int i = 1; i < reslen; i++)
    {
        sum[i] = sum[i - 1] + out[i];
    }
    delete[] out;
    long long result = 0;
    for (int i = 0; i < n; i++)
    {
        result += sum[reslen - 1] - sum[arr[i]];
        result -= (long long)(n - 1 - i) * i;
        result -= n - 1;
        result -= (long long)(n - 1 - i) * (n - 2 - i) / 2;
    }
    cout << result << endl;
}

int lengthExtend(int max)
{
    int temp = max;
    int add = 2;
    while (temp > 1)
    {
        temp = temp >> 1;
        add = add << 1;
    }
    return add;
}

complex<double> *transform(complex<double> *a, const int n, int flag)
{
    if (n == 1)
        return a;

    const int m = n / 2;
    complex<double> *a1 = new complex<double>[m];
    complex<double> *a2 = new complex<double>[m];
    complex<double> *y = new complex<double>[n];
    for (int i = 0; i < m; i++)
    {
        a1[i] = a[i << 1];
        a2[i] = a[(i << 1) + 1];
    }
    complex<double> *e = transform(a1, m, flag);
    complex<double> *d = transform(a2, m, flag);
    for (int i = 0; i < m; i++)
    {
        complex<double> x = complex<double>(cos(-2 * M_PI / n * i), flag * sin(-2 * M_PI / n * i));
        y[i] = e[i] + x * d[i];
        y[i + m] = e[i] - x * d[i];
    }
    delete[] a1;
    delete[] a2;
    return y;
}

complex<double> *dft(complex<double> *a, const int n)
{
    complex<double> *y = transform(a, n, 1);
    return y;
}

complex<double> *idft(complex<double> *a, const int n)
{
    complex<double> *y = transform(a, n, -1);
    for (int i = 0; i < n; i++)
        y[i] /= n;
    return y;
}

void multiply(const int *a1, const int n1, const int *a2, const int n2, long long *res)
{
    int n = 1;
    while (n < n1 + n2)
        n = n << 1;
    complex<double> *c1 = new complex<double>[n];
    complex<double> *c2 = new complex<double>[n];
    for (int i = 0; i < n1; i++)
        c1[i].real(a1[i]);
    for (int i = 0; i < n2; i++)
        c2[i].real(a2[i]);
    c1 = dft(c1, n);
    c2 = dft(c2, n);
    for (int i = 0; i < n; i++)
    {
        c1[i] *= c2[i];
    }
    c1 = idft(c1, n);
    for (int i = 0; i < n; i++)
    {
        res[i] = (int)(c1[i].real() + 0.5);
    }
    delete[] c1;
    delete[] c2;
}