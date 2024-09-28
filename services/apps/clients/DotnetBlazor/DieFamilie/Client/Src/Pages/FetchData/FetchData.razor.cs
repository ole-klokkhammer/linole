
using System.Net.Http.Json;
using DieFamilie.Shared;

namespace DieFamilie.Client.Src.Pages.FetchData;

public partial class FetchData {

    private WeatherForecast[]? forecasts;

    protected override async Task OnInitializedAsync() {
        forecasts = await Http.GetFromJsonAsync<WeatherForecast[]>("WeatherForecast");
    }
}
