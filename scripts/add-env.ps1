# �v���W�F�N�g�����ɔz�u����.env�́Ajava���Ɠǂݎ��Ȃ��̂�
# 

$envAbsolutePath = Join-Path (Split-Path $MyInvocation.MyCommand.Source) ".env"

Get-Content $envAbsolutePath | foreach {
    # ��s�ƃR�����g�s���X�L�b�v
    if ($_ -match "^$") { return; }
    if ($_ -match "^\s*#.*$") { return; }

    if ($_ -match "\s*([a-zA-Z0-9_]+)\s*`=\s*(.*)"){
        # $matches[0] # => $_
        $envKey = $matches[1]
        $envValue = $matches[2]
        
        Set-Content -Path env:$envKey -Value $envValue
        "�o�^����: " + $_
    } else {
        "�Y���Ȃ�: " + $_
    }
}
