# プロジェクト直下に配置した.envは、javaだと読み取れないので
# 

$envAbsolutePath = Join-Path (Split-Path $MyInvocation.MyCommand.Source) ".env"

Get-Content $envAbsolutePath | foreach {
    # 空行とコメント行をスキップ
    if ($_ -match "^$") { return; }
    if ($_ -match "^\s*#.*$") { return; }

    if ($_ -match "\s*([a-zA-Z0-9_]+)\s*`=\s*(.*)"){
        # $matches[0] # => $_
        $envKey = $matches[1]
        $envValue = $matches[2]
        
        Set-Content -Path env:$envKey -Value $envValue
        "登録完了: " + $_
    } else {
        "該当なし: " + $_
    }
}
