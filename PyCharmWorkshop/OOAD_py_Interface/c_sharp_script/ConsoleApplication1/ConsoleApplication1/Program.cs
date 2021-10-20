// using IronPython.Hosting;
// using Microsoft.Scripting.Hosting;
using System;

namespace CSharpCallPython
{
    class Program
    {
        static void Main(string[] args)
        {
            // ScriptEngine pyEngine = Python.CreateEngine();//创建Python解释器对象
            // dynamic py = pyEngine.ExecuteFile(@"test.py");//读取脚本文件
            // string reStr = py.add(2, 4, 1002);//调用脚本文件中对应的函数
            string reStr = "1";
            Console.WriteLine(reStr);

            Console.ReadKey();
        }
    }
}