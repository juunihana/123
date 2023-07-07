package greetings

import(
	"fmt"
)

func hello(name string) string {
	var message := fmt.Sprintf("Hi, %v. Welcome!", name)
	return message
}