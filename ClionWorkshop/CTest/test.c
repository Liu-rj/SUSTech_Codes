#include <stdio.h>
#include <stdlib.h>

int main()
{
char *char_ptr = (char *) malloc(20);
char *ptr;
char *rest;
sprintf(char_ptr, "%f,%f", 123.456,65.321);
double res = strtod(char_ptr, &ptr);
ptr = ptr + 1;
double ret = strtod(ptr, &rest);
printf("The number(double) is %lf %lf\n", res, ret);
printf("String part is |%s|\n", rest);
free(char_ptr);
}
