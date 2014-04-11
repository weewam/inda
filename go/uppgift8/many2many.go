// Stefan Nilsson 2013-03-13
// Ibrahim Asfadai 2014-04-02

// This is a testbed to help you understand channels better.
package main

import (
	"fmt"
	"math/rand"
	"strconv"
	"sync"
	"time"
)

func main() {
	// Use different random numbers each time this program is executed.
	rand.Seed(time.Now().Unix())

	const strings = 32
	const producers = 4
	const consumers = 4

	before := time.Now()
	ch := make(chan string)

	wgpProducers := new(sync.WaitGroup)
	wgpConsumers := new(sync.WaitGroup)

	wgpProducers.Add(producers)
	wgpConsumers.Add(consumers)

	for i := 0; i < producers; i++ {
		go Produce("p"+strconv.Itoa(i), strings/producers, ch, wgpProducers)
	}

	for i := 0; i < consumers; i++ {
		go Consume("c"+strconv.Itoa(i), ch, wgpConsumers)
	}

	wgpProducers.Wait() // Wait for all producers to finish.
	close(ch)
	wgpConsumers.Wait() // Wait for all consumers to finish.

	fmt.Println("time:", time.Now().Sub(before))
}

// Produce sends n different strings on the channel and notifies wg when done.
func Produce(id string, n int, ch chan<- string, wg *sync.WaitGroup) {
	for i := 0; i < n; i++ {
		RandomSleep(100) // Simulate time to produce data.
		ch <- id + ":" + strconv.Itoa(i)
	}

	wg.Done()
}

// Consume prints strings received from the channel until the channel is closed.
func Consume(id string, ch <-chan string, wg *sync.WaitGroup) {
	for s := range ch {
		fmt.Println(id, "received", s)
		RandomSleep(100) // Simulate time to consume data.
	}
	
	wg.Done()
}

// RandomSleep waits for x ms, where x is a random number, 0 â‰¤ x < n,
// and then returns.
func RandomSleep(n int) {
	time.Sleep(time.Duration(rand.Intn(n)) * time.Millisecond)
}
