#define _USE_MATH_DEFINES
#include <iostream>
#include <cmath>
#include <bits/stdc++.h>

using namespace std;

#define MAXN 16

inline void multiply(const int *a1, const int n1, const int *a2, const int n2, int *res);

int main()
{
    int *a1 = new int[8]{0, 1, 0, 2, 1, 0, 0, 0};
    int *a2 = new int[8]{0, 1, 0, 2, 1, 0, 0, 0};
    int *a3 = new int[16]{};
    multiply(a1, 8, a2, 8, a3);
    for (int i = 0; i < 16; i++)
    {
        cout << a3[i] << " ";
    }
}

struct FastFourierTransform
{
    std::complex<double> omega[MAXN], omegaInverse[MAXN];

    void init(const int n)
    {
        for (int i = 0; i < n; i++)
        {
            omega[i] = std::complex<double>(cos(2 * M_PI / n * i), sin(2 * M_PI / n * i));
            omegaInverse[i] = std::conj(omega[i]);
        }
    }

    void transform(std::complex<double> *a, const int n, const std::complex<double> *omega)
    {
        int k = 0;
        while ((1 << k) < n)
            k++;
        for (int i = 0; i < n; i++)
        {
            int t = 0;
            for (int j = 0; j < k; j++)
                if (i & (1 << j))
                    t |= (1 << (k - j - 1));
            if (i < t)
                std::swap(a[i], a[t]);
        }

        for (int l = 2; l <= n; l *= 2)
        {
            int m = l / 2;
            for (std::complex<double> *p = a; p != a + n; p += l)
            {
                for (int i = 0; i < m; i++)
                {
                    std::complex<double> t = omega[n / l * i] * p[m + i];
                    p[m + i] = p[i] - t;
                    p[i] += t;
                }
            }
        }
    }

    void dft(std::complex<double> *a, const int n)
    {
        transform(a, n, omega);
    }

    void idft(std::complex<double> *a, const int n)
    {
        transform(a, n, omegaInverse);
        for (int i = 0; i < n; i++)
            a[i] /= n;
    }
} fft;

inline void multiply(const int *a1, const int n1, const int *a2, const int n2, int *res)
{
    int n = 1;
    while (n < n1 + n2)
        n = n << 1;
    static std::complex<double> c1[MAXN], c2[MAXN];
    for (int i = 0; i < n1; i++)
        c1[i].real(a1[i]);
    for (int i = 0; i < n2; i++)
        c2[i].real(a2[i]);
    fft.init(n);
    fft.dft(c1, n), fft.dft(c2, n);
    for (int i = 0; i < n; i++)
    {
        c1[i] *= c2[i];
    }
    fft.idft(c1, n);
    for (int i = 0; i < n; i++)
    {
        res[i] = (int) (c1[i].real() + 0.5);
    }
}