package main

import "fmt"
import "time"

// Prints out the numbers 1 - 11
func main() {
	ch := make(chan int)
	done := make(chan bool)

	go Print(ch, done)

	for i := 1; i <= 11; i++ {
		ch <- i
	}
	close(ch)

	<-done
}

// Print prints all numbers sent on the channel.
// The function returns when the channel is closed.
func Print(ch <-chan int, done chan<- bool) {
	for n := range ch { // reads from channel until it's closed
		time.Sleep(time.Second)
		fmt.Println(n)
	}

	done <- true
}
