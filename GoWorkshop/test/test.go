package main

func main() {
	code, b := Encode(31.1932993, 121.43960190000007, 6)
	println(code)
	println(b.MinLat, b.MaxLat, b.MinLng, b.MaxLng)
}
