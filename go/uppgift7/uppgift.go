// Uppgift 1
package main

import (
	"fmt"
	"math"
)

func Sqrt(x float64) float64 {
	z := float64(1)
	v := x

	for math.Abs(z-v) > 1e-8 {
		v = z
		z = z - (math.Pow(z, 2)-x)/(2*z)
	}

	return z
}

func main() {
	fmt.Println(Sqrt(2))
	fmt.Println(math.Sqrt(2))
}

// Uppgift 2
package main

import "code.google.com/p/go-tour/pic"

func Pic(dx, dy int) [][]uint8 {
	matrix := make([][]uint8, dy)

	for y := 0; y < dy; y++ {
		matrix[y] = make([]uint8, dx)

		for x := 0; x < dx; x++ {
			matrix[y][x] = uint8(x * y)
		}
	}

	return matrix
}

func main() {
	pic.Show(Pic)
}

// Uppgift 3
package main

import (
	"code.google.com/p/go-tour/wc"
	"strings"
)

func WordCount(s string) map[string]int {
	m := make(map[string]int)

	for _, value := range strings.Fields(s) {
		m[value] += 1
	}

	return m
}

func main() {
	wc.Test(WordCount)
}


// Uppgift 4
package main

import "fmt"

// fibonacci is a function that returns
// a function that returns an int.
func fibonacci() func() int {
    i, j := 0, 1
    
    return func() int {
        v := i + j
        i, j = j, v
        return v
    }
}

func main() {
    f := fibonacci()
    for i := 0; i < 10; i++ {
        fmt.Println(f())
    }
}

// Uppgift 5
package main

import (
	"fmt"
	"time"
)

func Remind(text string, paus time.Duration) {
	for {
		time.Sleep(paus * 3600 * 1e9)
		fmt.Println("Klockan är " + time.Now().Format("15:04:05") + ": " + text)
	}
}

func main() {
	go Remind("Dags att äta", 3)
	go Remind("Dags att arbeta", 8)
	go Remind("Dags att sova", 24)

	select {}
}


// Uppgift 6
package main

// Add adds the numbers in a and sends the result on res.
func Add(a []int, res chan<- int) {
    // TODO
}

func main() {
    a := []int{1, 2, 3, 4, 5, 6, 7}

    n := len(a)
    ch := make(chan int)
    go Add(a[:n/2], ch)
    go Add(a[n/2:], ch)

    // TODO: Get the subtotals from the channel and print their sum.
}