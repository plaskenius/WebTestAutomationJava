WinWaitActive("Windows Security","","10")

If WinExists("Windows Security") Then

Send("cs-ws-s-webTESTER{TAB}")

Send("MG-9JHce&789ac7{Enter}")

EndIf
