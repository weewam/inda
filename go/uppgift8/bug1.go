package main

import "fmt"

//Prints out hello world
func main() {
	ch := make(chan string)
	go func() {
		ch <- "Hello world!"
	}()

	fmt.Println(<-ch)
}
