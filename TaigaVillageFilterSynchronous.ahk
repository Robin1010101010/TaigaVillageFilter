#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.


ExitWorld()
{
   send {Esc}{Tab}{Enter}
   sleep, 100
   send {Esc}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Enter}
   sleep, 100
   send {Esc}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Enter}{Esc}
}


CreateNewWorld()
{
   send {Tab}{Enter}{Tab}{Tab}{Tab}{Enter}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{NumpadSub}TaigaVillageFilterSeed{NumpadSub}{Tab}{Tab}{Enter}{Enter}{Enter}{Tab}{Tab}{Tab}{Tab}{Enter}{Tab}{Tab}{Tab}^v{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Enter}
}


#IfWinActive, Minecraft
{

U:: ; Reset 
   sleep, 1000
   ExitWorld()
   sleep, 3000
   Run, TaigaVillageFilter.jar
   while !FileExist("seedFound.txt") {
      sleep, 1000
   }
   FileDelete, seedFound.txt
   CreateNewWorld()
return

}
