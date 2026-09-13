Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "      Cyber Crime Management System - Launcher" -ForegroundColor Cyan
Write-Host "=========================================================`n" -ForegroundColor Cyan

Set-Location -Path $PSScriptRoot

# Check JDK compiler
if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    Write-Host "[ERROR] 'javac' was not found in your PATH." -ForegroundColor Red
    Write-Host "Please ensure the Java Development Kit (JDK) is installed and added to PATH.`n"
    Read-Host "Press Enter to exit..."
    exit 1
}

# Check Java runtime
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "[ERROR] 'java' was not found in your PATH." -ForegroundColor Red
    Write-Host "Please ensure Java is installed and added to PATH.`n"
    Read-Host "Press Enter to exit..."
    exit 1
}

# Ensure bin directory exists
if (-not (Test-Path -Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}

Write-Host "[1/2] Compiling Java source files..." -ForegroundColor Yellow

$compileSuccess = $false
try {
    javac -encoding UTF-8 -d bin (Get-ChildItem frontend\*.java) (Get-ChildItem backend\*.java) 2>$null
    if ($LASTEXITCODE -eq 0) {
        $compileSuccess = $true
    }
} catch {
    $compileSuccess = $false
}

if (-not $compileSuccess) {
    # Fallback to frontend only
    javac -encoding UTF-8 -d bin (Get-ChildItem frontend\*.java)
    if ($LASTEXITCODE -ne 0) {
        Write-Host "`n[ERROR] Compilation failed! Please check error output above." -ForegroundColor Red
        Read-Host "Press Enter to exit..."
        exit 1
    }
}

Write-Host "[OK] Compilation successful!`n" -ForegroundColor Green
Write-Host "[2/2] Launching Application (MainApp)...`n" -ForegroundColor Cyan

java -cp bin MainApp
