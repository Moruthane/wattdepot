# Introduction #

Since the WattDepot RestApi uses properties to record domain-specific information, it is important for there to be a list of what property values are used to record quantities that might be recorded from a data source. The properties are documented here for reference by implementors or others retrieving data from WattDepot.

# Power-Related Properties #

These are properties that can be returned as part of a SensorData resource. There are two types of properties,
These properties related to electrical power are often sampled through the use of automated meters. Note that come directly from measuring devices (sensor data) and those that are calculated based on sensor data (calculated properties). Note that some properties can be found in both sensor data and calculated values.

## Sensor Data Properties ##

Properties from meters generally measure the power or energy flowing through them, but whether that power/energy is being consumed or generated depends on what is on either side of the meter. Thus a client retrieving data from a meter requires some metadata about what the meter is recording in order to tell which properties it should use in reporting the sensor data.

| **Property name** | **Meaning** |
|:------------------|:------------|
| powerConsumed     | A floating point number representing an amount of power consumed by some entity in Watts |
| energyConsumedToDate | A floating point number representing an amount of energy consumed by some entity in Watt-hours. This number starts at 0 and increases every time sampled, so the energy consumed between two points in time is the difference between the two energyConsumedToDate values. If the second value is less than the first value, then the counter has been reset, and energy consumption will need to be determined in another fashion (perhaps using power data). |
| powerGenerated    | A floating point number representing an amount of power generated by some entity in Watts |
| energyGeneratedToDate | A floating point number representing an amount of energy generated by some entity in Watt-hours. This number starts at 0 and increases every time sampled, so the energy generated between two points in time is the difference between the two energyGeneratedToDate values. If the second value is less than the first value, then the counter has been reset, and energy generation will need to be determined in another fashion (perhaps using power data). |

## Calculated Properties ##

| **Property name** | **Meaning** |
|:------------------|:------------|
| powerConsumed     | A floating point number representing an amount of power consumed by some entity in Watts |
| energyConsumed    | A floating point number representing an amount of energy consumed by some entity in Watt hours |
| powerGenerated    | A floating point number representing an amount of power generated by some entity in Watts |
| energyGenerated   | A floating point number representing an amount of energy generated by some entity in Watt hours |
| carbonEmitted     | A floating point number representing an amount of carbon emitted by some entity in lbs CO2 equivalent |
| interpolated      | Set to "true" when the other values in this SensorData object have been generated by linear interpolation |

## Source Properties ##

| **Property name** | **Meaning** |
|:------------------|:------------|
| carbonIntensity   | A floating point number representing the amount of greenhouse gas emissions this Source produces in lbs CO2 equivalent per MWh of power produced. This property is really only appropriate for Sources that track the generation of power. Feels kinda dirty to be using non-metric units, but our data from CARMA comes this way. |
| fuelType          | A tag indicating the type of fuel used by a power plant. This property is really only appropriate for Sources that track the generation of power. The values currently in use for Oahu are "LSFO" (low sulfur fuel oil), "diesel", "waste" (municipal waste), and "coal". |
| updateInterval    | An integer representing the rate at which this source is expected to produce sensor data. For example, a value of 60 could indicate a source that is polled once every 60 seconds for sensor data. |
| energyDirection   | A string indicating what direction energy flows through this Source. This can be useful for characterizing the source or for displaying its sensor data. Valid values are: "consumer" (sensor data would contain powerConsumed and/or energyConsumedToDate), "generator" (sensor data would contain powerGenerated and/or energyGeneratedToDate), and "consumer+generator" (sensor data could contain all four properties already listed). Note that the creator of the source is responsible for setting this property such that it accurately reflects the sensor data being stored, WattDepot makes no effort to enforce the energyDirection. |
| supportsEnergyCounters | A flag indicating whether this source supports energy counters (energyConsumedToDate and/or energyGeneratedToDate). If set to "true", then energy calculated properties will be derived from the energy counters, rather than the less accurate method of integrating over the instantaneous power data. |
| cacheWindowLength | An integer defining cache window length in minutes. This specifies how long to keep SensorData resources in memory before expiring them. |
| cacheCheckpointInterval | An integer defining ephemeral data checkpoint interval in minutes. This specifies how often to store sensor data to persistant disk storage (as opposed to cache). |

# Weather Properties #

## Wind Properties ##

## Solar Properties ##