package com.streakify.android.utils

class Countries {

    companion object {
        var globalCountryList : List<Country> ? = null
    }

    fun getCountryList() : List<Country> {
        if ( globalCountryList != null ) {
            return globalCountryList!!
        }

        initCountryList()
        return globalCountryList!!
    }

    private fun initCountryList() {
        val countryList  =  mutableListOf<Country>()
        countryList.add(Country("AFG", "Afghanistan", "+93", "AFN"))
        countryList.add(Country("ALA", "Åland Islands", "+358", "EUR"))
        countryList.add(Country("ALB", "Albania", "+355", "ALL"))
        countryList.add(Country("DZA", "Algeria", "+213", "DZD"))
        countryList.add(Country("ASM", "American Samoa", "+1", "$"))
        countryList.add(Country("AND", "Andorra", "+376", "EUR"))
        countryList.add(Country("AGO", "Angola", "+244", "AOA"))
        countryList.add(Country("AIA", "Anguilla", "+1", "XCD"))
        countryList.add(Country("ATG", "Antigua and Barbuda", "+1", "XCD"))
        countryList.add(Country("ARG", "Argentina", "+54", "ARS"))
        countryList.add(Country("ARM", "Armenia", "+374", "AMD"))
        countryList.add(Country("ABW", "Aruba", "+297", "AWG"))
        countryList.add(Country("AUS", "Australia", "+61", "AUD"))
        countryList.add(Country("AUT", "Austria", "+43", "EUR"))
        countryList.add(Country("AZE", "Azerbaijan", "+994", "AZN"))
        countryList.add(Country("BHS", "Bahamas", "+1", "BSD"))
        countryList.add(Country("BHR", "Bahrain", "+973", "BHD"))
        countryList.add(Country("BGD", "Bangladesh", "+880", "৳"))
        countryList.add(Country("BRB", "Barbados", "+1", "BBD"))
        countryList.add(Country("BLR", "Belarus", "+375", "BYN"))
        countryList.add(Country("BEL", "Belgium", "+32", "EUR"))
        countryList.add(Country("BLZ", "Belize", "+501", "BZD"))
        countryList.add(Country("BEN", "Benin", "+229", "XOF"))
        countryList.add(Country("BMU", "Bermuda", "+1", "BMD"))
        countryList.add(Country("BTN", "Bhutan", "+975", "BTN"))
        countryList.add(Country("BOL", "Bolivia, Plurinational State Of", "+591", "BOB"))
        countryList.add(Country("BIH", "Bosnia And Herzegovina", "+387", "BAM"))
        countryList.add(Country("BWA", "Botswana", "+267", "BWP"))
        countryList.add(Country("BRA", "Brazil", "+55", "BRL"))
        countryList.add(Country("IOT", "British Indian Ocean Territory", "+246", "$"))
        countryList.add(Country("VGB", "British Virgin Islands", "+1", "$"))
        countryList.add(Country("BRN", "Brunei Darussalam", "+673", "BND"))
        countryList.add(Country("BGR", "Bulgaria", "+359", "BGN"))
        countryList.add(Country("BFA", "Burkina Faso", "+226", "XOF"))
        countryList.add(Country("BDI", "Burundi", "+257", "BIF"))
        countryList.add(Country("KHM", "Cambodia", "+855", "KHR"))
        countryList.add(Country("CMR", "Cameroon", "+237", "XAF"))
        countryList.add(Country("CAN", "Canada", "+1", "CAD"))
        countryList.add(Country("CPV", "Cape Verde", "+238", "CVE"))
        countryList.add(Country("CYM", "Cayman Islands", "+1", "KYD"))
        countryList.add(Country("CAF", "Central African Republic", "+236", "XAF"))
        countryList.add(Country("TCD", "Chad", "+235", "XAF"))
        countryList.add(Country("CHL", "Chile", "+56", "CLP"))
        countryList.add(Country("CHN", "China", "+86", "CNY"))
        countryList.add(Country("CXR", "Christmas Island", "+61", "AUD"))
        countryList.add(Country("CCK", "Cocos (keeling) Islands", "+61", "AUD"))
        countryList.add(Country("COL", "Colombia", "+57", "COP"))
        countryList.add(Country("COM", "Comoros", "+269", "KMF"))
        countryList.add(Country("COG", "Congo", "+242", "XAF"))
        countryList.add(Country("COD", "Congo, The Democratic Republic Of The", "+243", "CDF"))
        countryList.add(Country("COK", "Cook Islands", "+682", "NZD"))
        countryList.add(Country("CRI", "Costa Rica", "+506", "CRC"))
        countryList.add(Country("CIV", "Côte D'ivoire", "+225", "XOF"))
        countryList.add(Country("HRV", "Croatia", "+385", "HRK"))
        countryList.add(Country("CUB", "Cuba", "+53", "CUP"))
        countryList.add(Country("CUW", "Curaçao", "+599", "ANG"))
        countryList.add(Country("CYP", "Cyprus", "+357", "EUR"))
        countryList.add(Country("CZE", "Czech Republic", "+420", "CZK"))
        countryList.add(Country("DNK", "Denmark", "+45", "DKK"))
        countryList.add(Country("DJI", "Djibouti", "+253", "DJF"))
        countryList.add(Country("DMA", "Dominica", "+1", "XCD"))
        countryList.add(Country("DOM", "Dominican Republic", "+1", "DOP"))
        countryList.add(Country("ECU", "Ecuador", "+593", "$"))
        countryList.add(Country("EGY", "Egypt", "+20", "EGP"))
        countryList.add(Country("SLV", "El Salvador", "+503", "$"))
        countryList.add(Country("GNQ", "Equatorial Guinea", "+240", "XAF"))
        countryList.add(Country("ERI", "Eritrea", "+291", "ERN"))
        countryList.add(Country("EST", "Estonia", "+372", "EUR"))
        countryList.add(Country("ETH", "Ethiopia", "+251", "ETB"))
        countryList.add(Country("FLK", "Falkland Islands (malvinas)", "+500", "FKP"))
        countryList.add(Country("FRO", "Faroe Islands", "+298", "DKK"))
        countryList.add(Country("FJI", "Fiji", "+679", "FJD"))
        countryList.add(Country("FIN", "Finland", "+358", "EUR"))
        countryList.add(Country("FRA", "France", "+33", "EUR"))
        countryList.add(Country("GUF", "French Guyana", "+594", "EUR"))
        countryList.add(Country("PYF", "French Polynesia", "+689", "XPF"))
        countryList.add(Country("GAB", "Gabon", "+241", "XAF"))
        countryList.add(Country("GMB", "Gambia", "+220", "GMD"))
        countryList.add(Country("GEO", "Georgia", "+995", "GEL"))
        countryList.add(Country("DEU", "Germany", "+49", "EUR"))
        countryList.add(Country("GHA", "Ghana", "+233", "GHS"))
        countryList.add(Country("GIB", "Gibraltar", "+350", "GIP"))
        countryList.add(Country("GRC", "Greece", "+30", "EUR"))
        countryList.add(Country("GRL", "Greenland", "+299", "DKK"))
        countryList.add(Country("GRD", "Grenada", "+1", "XCD"))
        countryList.add(Country("GLP", "Guadeloupe", "+590", "EUR"))
        countryList.add(Country("GUM", "Guam", "+1", "$"))
        countryList.add(Country("GTM", "Guatemala", "+502", "GTQ"))
        countryList.add(Country("GGY", "Guernsey", "+44", "£"))
        countryList.add(Country("GIN", "Guinea", "+224", "GNF"))
        countryList.add(Country("GNB", "Guinea-bissau", "+245", "XOF"))
        countryList.add(Country("GUY", "Guyana", "+592", "GYD"))
        countryList.add(Country("HTI", "Haiti", "+509", "HTG"))
        countryList.add(Country("VAT", "Holy See (vatican City State)", "+379", "EUR"))
        countryList.add(Country("HND", "Honduras", "+504", "HNL"))
        countryList.add(Country("HKG", "Hong Kong", "+852", "HKD"))
        countryList.add(Country("HUN", "Hungary", "+36", "HUF"))
        countryList.add(Country("ISL", "Iceland", "+354", "ISK"))
        countryList.add(Country("IND", "India", "+91", "₹"))
        countryList.add(Country("IDN", "Indonesia", "+62", "IDR"))
        countryList.add(Country("IRN", "Iran, Islamic Republic Of", "+98", "IRR"))
        countryList.add(Country("IRQ", "Iraq", "+964", "IQD"))
        countryList.add(Country("IRL", "Ireland", "+353", "EUR"))
        countryList.add(Country("IMN", "Isle Of Man", "+44", "£"))
        countryList.add(Country("ISR", "Israel", "+972", "ILS"))
        countryList.add(Country("ITA", "Italy", "+39", "EUR"))
        countryList.add(Country("JAM", "Jamaica", "+1", "JMD"))
        countryList.add(Country("JPN", "Japan", "+81", "JPY"))
        countryList.add(Country("JEY", "Jersey", "+44", "£"))
        countryList.add(Country("JOR", "Jordan", "+962", "JOD"))
        countryList.add(Country("KAZ", "Kazakhstan", "+7", "KZT"))
        countryList.add(Country("KEN", "Kenya", "+254", "KES"))
        countryList.add(Country("KIR", "Kiribati", "+686", "AUD"))
        countryList.add(Country("KWT", "Kuwait", "+965", "KWD"))
        countryList.add(Country("KGZ", "Kyrgyzstan", "+996", "KGS"))
        countryList.add(Country("LAO", "Lao People's Democratic Republic", "+856", "LAK"))
        countryList.add(Country("LVA", "Latvia", "+371", "EUR"))
        countryList.add(Country("LBN", "Lebanon", "+961", "LBP"))
        countryList.add(Country("LSO", "Lesotho", "+266", "ZAR"))
        countryList.add(Country("LBR", "Liberia", "+231", "LRD"))
        countryList.add(Country("LBY", "Libya", "+218", "LYD"))
        countryList.add(Country("LIE", "Liechtenstein", "+423", "CHF"))
        countryList.add(Country("LTU", "Lithuania", "+370", "EUR"))
        countryList.add(Country("LUX", "Luxembourg", "+352", "EUR"))
        countryList.add(Country("MAC", "Macau", "+853", "MOP"))
        countryList.add(Country("MKD", "Macedonia (FYROM)", "+389", "MKD"))
        countryList.add(Country("MDG", "Madagascar", "+261", "MGA"))
        countryList.add(Country("MWI", "Malawi", "+265", "MWK"))
        countryList.add(Country("MYS", "Malaysia", "+60", "MYR"))
        countryList.add(Country("MDV", "Maldives", "+960", "MVR"))
        countryList.add(Country("MLI", "Mali", "+223", "XOF"))
        countryList.add(Country("MLT", "Malta", "+356", "EUR"))
        countryList.add(Country("MHL", "Marshall Islands", "+692", "$"))
        countryList.add(Country("MTQ", "Martinique", "+596", "EUR"))
        countryList.add(Country("MRT", "Mauritania", "+222", "MRO"))
        countryList.add(Country("MUS", "Mauritius", "+230", "MUR"))
        countryList.add(Country("MYT", "Mayotte", "+262", "EUR"))
        countryList.add(Country("MEX", "Mexico", "+52", "MXN"))
        countryList.add(Country("FSM", "Micronesia, Federated States Of", "+691", "$"))
        countryList.add(Country("MDA", "Moldova, Republic Of", "+373", "MDL"))
        countryList.add(Country("MCO", "Monaco", "+377", "EUR"))
        countryList.add(Country("MNG", "Mongolia", "+976", "MNT"))
        countryList.add(Country("MNE", "Montenegro", "+382", "EUR"))
        countryList.add(Country("MSR", "Montserrat", "+1", "XCD"))
        countryList.add(Country("MAR", "Morocco", "+212", "MAD"))
        countryList.add(Country("MOZ", "Mozambique", "+258", "MZN"))
        countryList.add(Country("MMR", "Myanmar", "+95", "MMK"))
        countryList.add(Country("NAM", "Namibia", "+264", "NAD"))
        countryList.add(Country("NRU", "Nauru", "+674", "AUD"))
        countryList.add(Country("NPL", "Nepal", "+977", "रू"))
        countryList.add(Country("NLD", "Netherlands", "+31", "EUR"))
        countryList.add(Country("NCL", "New Caledonia", "+687", "XPF"))
        countryList.add(Country("NZL", "New Zealand", "+64", "NZD"))
        countryList.add(Country("NIC", "Nicaragua", "+505", "NIO"))
        countryList.add(Country("NER", "Niger", "+227", "XOF"))
        countryList.add(Country("NGA", "Nigeria", "+234", "NGN"))
        countryList.add(Country("NIU", "Niue", "+683", "NZD"))
        countryList.add(Country("NFK", "Norfolk Islands", "+672", "AUD"))
        countryList.add(Country("PRK", "North Korea", "+850", "KPW"))
        countryList.add(Country("MNP", "Northern Mariana Islands", "+1", "$"))
        countryList.add(Country("NOR", "Norway", "+47", "NOK"))
        countryList.add(Country("OMN", "Oman", "+968", "OMR"))
        countryList.add(Country("PAK", "Pakistan", "+92", "₨"))
        countryList.add(Country("PLW", "Palau", "+680", "$"))
        countryList.add(Country("PSE", "Palestine", "+970", "ILS"))
        countryList.add(Country("PAN", "Panama", "+507", "PAB"))
        countryList.add(Country("PNG", "Papua New Guinea", "+675", "PGK"))
        countryList.add(Country("PRY", "Paraguay", "+595", "PYG"))
        countryList.add(Country("PER", "Peru", "+51", "PEN"))
        countryList.add(Country("PHL", "Philippines", "+63", "₱"))
        countryList.add(Country("PCN", "Pitcairn Islands", "+870", "NZD"))
        countryList.add(Country("POL", "Poland", "+48", "PLN"))
        countryList.add(Country("PRT", "Portugal", "+351", "EUR"))
        countryList.add(Country("PRI", "Puerto Rico", "+1", "$"))
        countryList.add(Country("QAT", "Qatar", "+974", "QAR"))
        countryList.add(Country("REU", "Réunion", "+262", "EUR"))
        countryList.add(Country("ROU", "Romania", "+40", "RON"))
        countryList.add(Country("RUS", "Russian Federation", "+7", "RUB"))
        countryList.add(Country("RWA", "Rwanda", "+250", "RWF"))
        countryList.add(Country("BLM", "Saint Barthélemy", "+590", "EUR"))
        countryList.add(Country("SHN", "Saint Helena, Ascension And Tristan Da Cunha", "+290", "SHP"))
        countryList.add(Country("KNA", "Saint Kitts and Nevis", "+1", "XCD"))
        countryList.add(Country("LCA", "Saint Lucia", "+1", "XCD"))
        countryList.add(Country("MAF", "Saint Martin", "+590", "EUR"))
        countryList.add(Country("SPM", "Saint Pierre And Miquelon", "+508", "EUR"))
        countryList.add(Country("VCT", "Saint Vincent & The Grenadines", "+1", "XCD"))
        countryList.add(Country("WSM", "Samoa", "+685", "WST"))
        countryList.add(Country("SMR", "San Marino", "+378", "EUR"))
        countryList.add(Country("STP", "Sao Tome And Principe", "+239", "STD"))
        countryList.add(Country("SAU", "Saudi Arabia", "+966", "SAR"))
        countryList.add(Country("SEN", "Senegal", "+221", "XOF"))
        countryList.add(Country("SRB", "Serbia", "+381", "RSD"))
        countryList.add(Country("SYC", "Seychelles", "+248", "SCR"))
        countryList.add(Country("SLE", "Sierra Leone", "+232", "SLL"))
        countryList.add(Country("SGP", "Singapore", "+65", "SGD"))
        countryList.add(Country("SXM", "Sint Maarten", "+1", "ANG"))
        countryList.add(Country("SVK", "Slovakia", "+421", "EUR"))
        countryList.add(Country("SVN", "Slovenia", "+386", "EUR"))
        countryList.add(Country("SLB", "Solomon Islands", "+677", "SBD"))
        countryList.add(Country("SOM", "Somalia", "+252", "SOS"))
        countryList.add(Country("ZAF", "South Africa", "+27", "ZAR"))
        countryList.add(Country("KOR", "South Korea", "+82", "KRW"))
        countryList.add(Country("SSD", "South Sudan", "+211", "SSP"))
        countryList.add(Country("ESP", "Spain", "+34", "EUR"))
        countryList.add(Country("LKA", "Sri Lanka", "+94", "LKR"))
        countryList.add(Country("SDN", "Sudan", "+249", "SDG"))
        countryList.add(Country("SUR", "Suriname", "+597", "SRD"))
        countryList.add(Country("SWZ", "Swaziland", "+268", "SZL"))
        countryList.add(Country("SWE", "Sweden", "+46", "SEK"))
        countryList.add(Country("CHE", "Switzerland", "+41", "CHF"))
        countryList.add(Country("SYR", "Syrian Arab Republic", "+963", "SYP"))
        countryList.add(Country("TWN", "Taiwan", "+886", "TWD"))
        countryList.add(Country("TJK", "Tajikistan", "+992", "TJS"))
        countryList.add(Country("TZA", "Tanzania, United Republic Of", "+255", "TZS"))
        countryList.add(Country("THA", "Thailand", "+66", "THB"))
        countryList.add(Country("TLS", "Timor-leste", "+670", "$"))
        countryList.add(Country("TGO", "Togo", "+228", "XOF"))
        countryList.add(Country("TKL", "Tokelau", "+690", "NZD"))
        countryList.add(Country("TON", "Tonga", "+676", "TOP"))
        countryList.add(Country("TTO", "Trinidad & Tobago", "+1", "TTD"))
        countryList.add(Country("TUN", "Tunisia", "+216", "TND"))
        countryList.add(Country("TUR", "Turkey", "+90", "TRY"))
        countryList.add(Country("TKM", "Turkmenistan", "+993", "TMT"))
        countryList.add(Country("TCA", "Turks and Caicos Islands", "+1", "$"))
        countryList.add(Country("TUV", "Tuvalu", "+688", "AUD"))
        countryList.add(Country("UGA", "Uganda", "+256", "UGX"))
        countryList.add(Country("UKR", "Ukraine", "+380", "UAH"))
        countryList.add(Country("ARE", "United Arab Emirates (UAE)", "+971", "AED"))
        countryList.add(Country("GBR", "United Kingdom", "+44", "£"))
        countryList.add(Country("USA", "United States (USA)", "+1", "$"))
        countryList.add(Country("URY", "Uruguay", "+598", "UYU"))
        countryList.add(Country("VIR", "US Virgin Islands", "+1", "$"))
        countryList.add(Country("UZB", "Uzbekistan", "+998", "UZS"))
        countryList.add(Country("VUT", "Vanuatu", "+678", "VUV"))
        countryList.add(Country("VEN", "Venezuela, Bolivarian Republic Of", "+58", "VEF"))
        countryList.add(Country("VNM", "Vietnam", "+84", "VND"))
        countryList.add(Country("WLF", "Wallis And Futuna", "+681", "XPF"))
        countryList.add(Country("YEM", "Yemen", "+967", "YER"))
        countryList.add(Country("ZMB", "Zambia", "+260", "ZMW"))
        countryList.add(Country("ZWE", "Zimbabwe", "+263", "$"))
        globalCountryList = countryList
    }
}