if (!([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")) { Start-Process powershell.exe "-NoProfile -NonInteractive -ExecutionPolicy Bypass -File `"$PSCommandPath`"" -Verb RunAs; exit }


Function Get-browserSignInValue {
    # $IsWindows will PowerShell Core but not on PowerShell 5 and below, but $Env:OS does
    # this way you can safely check whether the current machine is running Windows pre and post PowerShell Core
    If ($IsWindows -or $Env:OS) {
        Try {
            Get-ItemPropertyValue -Path 'HKLM:\SOFTWARE\Policies\Microsoft\Edge\' -Name browserSignIn -ErrorAction Stop
        }
        Catch {
            Throw "browserSignIn not found in registry";
        }
    }
}

# If broswer sign is enabled (2), set to disabled (0) 
$browserSignInValue = Get-browserSignInValue -ErrorAction Stop;
Write-Output "$(Get-Date -format 'u') Microsoft Edge browserSignIn registry value $browserSignInValue found on machine"
If ($browserSignInValue -eq 2) {

    Write-Host "$(Get-Date -format 'u') Setting browserSignIn value to 0..."
    Set-ItemProperty -Path 'HKLM:\SOFTWARE\Policies\Microsoft\Edge\' -Name BrowserSignIn -Value 0 -Type DWord


} else {
Write-Host "$(Get-Date -format 'u') browserSignIn value is already 0, continuing with test."
}
