#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.


ExitWorld()
{
   send {Esc}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Enter}
}


SelectSeed()
{
   send +{down}^c{backspace}^s
}


CreateNewWorld()
{
   send {Tab}{Enter}{Tab}{Tab}{Tab}{Enter}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{backspace}{NumpadSub}TaigaVillageFilterSeed{NumpadSub}{Tab}{Tab}{Enter}{Enter}{Enter}{Tab}{Tab}{Tab}{Tab}{Enter}{Tab}{Tab}{Tab}^v{Tab}{Tab}{Tab}{Tab}{Tab}{Tab}{Enter}
}


#IfWinActive, Minecraft
{

U:: ; Reset 
   ExitWorld()
   sleep, 3000
   Run, TaigaVillageFilterSeeds.txt
   sleep, 1000
   WinActivate, ahk_exe notepad.exe
   sleep, 1000
   SelectSeed()
   sleep, 1000
   process, close, notepad.exe
   sleep, 1000
   WinActivate, ahk_exe javaw.exe
   sleep, 1000
   CreateNewWorld()
return

}
