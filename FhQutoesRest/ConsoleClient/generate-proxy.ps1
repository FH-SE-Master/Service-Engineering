# Install AutoRest with
# npm install -g autorest
# autorest --reset --version=latest-release

$AutoRestCmd = "$env:USERPROFILE\AppData\Roaming\npm\autorest.cmd"
$ProjectFolder = $PSScriptRoot
$SwaggerFile = Join-Path $ProjectFolder "swagger.json"
$GeneratedSourcesFolder = Join-Path $ProjectFolder "Generated"

Invoke-WebRequest -Uri http://localhost:5001/swagger/v1/swagger.json -OutFile $SwaggerFile
& $AutoRestCmd --csharp --input-file="$SwaggerFile" --namespace="Sve2.FhQuotes.Client.Proxy" --output-folder="$GeneratedSourcesFolder" --override-client-name="QuotesClient" 