#include "SDL2/SDL.h"
#include "SDL2/SDL_image.h"
#include <iostream>

SDL_Window *window;
SDL_Renderer *renderer;

int main()
{
    if (SDL_Init(SDL_INIT_VIDEO) != 0)
    {
        std::cout << "Unable to initialize SDL. " << SDL_GetError() << std::endl;
    }
    else
    {
        window = SDL_CreateWindow("HinodeEngine v1.0", 100, 100, 1280, 720, SDL_WINDOW_SHOWN);
        if (window != 0)
        {
            std::cout << "Unable to create window. " << SDL_GetError() << std::endl;
        }
        else
        {
            renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
            if (renderer != 0)
            {
                std::cout << "Unable to create renderer. " << SDL_GetError() << std::endl;
            }
            else
            {

                SDL_SetRenderDrawColor(renderer, 0x3, 0x3, 0x3, 0xFF);
                SDL_RenderClear(renderer);

                SDL_Event event;
                int running = 1;
                while (running == 1)
                {
                    if (SDL_PollEvent(&event) {
                        if (event.type == SDL_QUIT)
                        {
                            running = 0;
                        }
                        SDL_Delay(5);
                }
                }
            }
        }
    }

    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(window);
    SDL_Quit();
    return 0;
}