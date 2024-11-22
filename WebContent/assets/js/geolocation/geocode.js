// 都道府県コードのマッピング
const prefectureMap = {
            "JP-01": "016000",
            "JP-02": "020000",
            "JP-03": "030000",
            "JP-04": "040000",
            "JP-05": "050000",
            "JP-06": "060000",
            "JP-07": "070000",
            "JP-08": "080000",
            "JP-09": "090000",
            "JP-10": "100000",
            "JP-11": "110000",
            "JP-12": "120000",
            "JP-13": "130000",
            "JP-14": "140000",
            "JP-15": "150000",
            "JP-16": "160000",
            "JP-17": "170000",
            "JP-18": "180000",
            "JP-19": "190000",
            "JP-20": "200000",
            "JP-21": "210000",
            "JP-22": "220000",
            "JP-23": "230000",
            "JP-24": "240000",
            "JP-25": "250000",
            "JP-26": "260000",
            "JP-27": "270000",
            "JP-28": "280000",
            "JP-29": "290000",
            "JP-30": "300000",
            "JP-31": "310000",
            "JP-32": "320000",
            "JP-33": "330000",
            "JP-34": "340000",
            "JP-35": "350000",
            "JP-36": "360000",
            "JP-37": "370000",
            "JP-38": "380000",
            "JP-39": "390000",
            "JP-40": "400000",
            "JP-41": "410000",
            "JP-42": "420000",
            "JP-43": "430000",
            "JP-44": "440000",
            "JP-45": "450000",
            "JP-46": "460100",
            "JP-47": "471000"
        };

const BASE_API_URL = "https://www.jma.go.jp/bosai/forecast/data/forecast/";

        function getCurrentLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.watchPosition(successCallback, errorCallback, {
                    enableHighAccuracy: true,
                    maximumAge: 0,
                    timeout: 5000
                });
            } else {
                document.getElementById("weather").textContent = "Geolocationはこのブラウザでサポートされていません。";
            }
        }

        function successCallback(position) {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;

            console.log(`緯度: ${latitude}, 経度: ${longitude}`);
            reverseGeocode(latitude, longitude);
        }

        function errorCallback(error) {
            switch(error.code) {
                case error.PERMISSION_DENIED:
                    alert("位置情報の取得が許可されませんでした。");
                    break;
                case error.POSITION_UNAVAILABLE:
                    alert("位置情報が取得できませんでした。");
                    break;
                case error.TIMEOUT:
                    alert("位置情報の取得に時間がかかりすぎてタイムアウトしました。");
                    break;
                default:
                    alert("不明なエラーが発生しました。");
                    break;
            }
        }

        function reverseGeocode(latitude, longitude) {
            const url = `https://nominatim.openstreetmap.org/reverse?lat=${latitude}&lon=${longitude}&format=json&accept-language=ja`;

            fetch(url)
                .then(response => response.json())
                .then(data => {
                    console.log("APIのレスポンスデータ:", data);
                    let prefecture = null;

                    if (data.address) {
                        const isoCode = data.address["ISO3166-2-lvl4"];
                        prefecture = prefectureMap[isoCode] || "不明な都道府県";
                    } else {
                        prefecture = "都道府県を特定できませんでした。";
                    }

                    // エリアコードをサーバーに送信して天気情報を取得
                    getWeather(prefecture);
                })
                .catch(error => {
                    console.error("逆ジオコーディングのエラー:", error);
                });
        }
        
        async function getWeather(areaCode) {
		    const apiUrl = `${BASE_API_URL}${areaCode}.json`;
		
		    try {
		        // APIからデータを取得
		        const weatherData = await fetch(apiUrl);
		        if (!weatherData.ok) {
		            throw new Error("天気情報の取得に失敗しました。");
		        }
		        
		        const jsonData = await weatherData.json();
		        // 今日の天気情報を抽出
		        const weatherDescription = parseWeatherData(jsonData);
		        document.getElementById("weather").textContent = weatherDescription;
		    } catch (error) {
		        console.error(error);
		        return "天気情報の取得に失敗しました。";
		    }
		}
		
		// JSONデータから今日の天気情報を抽出し、地域名を付加して自然な文章にするメソッド
		function parseWeatherData(jsonData) {
		    const forecast = jsonData[0]; // 最初の要素を取得
		    const timeSeries = forecast.timeSeries;
		    const todayWeather = timeSeries[0]; // 今日の天気情報
		    const areas = todayWeather.areas;
		    const areaWeather = areas[0]; // 最初のエリア情報
		    const weathers = areaWeather.weathers;
		    const weather = weathers[0].replaceAll("　", ""); // 不要な空白を削除
		    const areaName = areaWeather.area.name; // 地域名の取得
		    	
		    return `${areaName}の天気は、${weather}`;
		}

        // ページ読み込み時に現在位置を取得
        window.onload = getCurrentLocation;