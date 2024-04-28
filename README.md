# Tidal API

A lightweight wrapper for Tidal's Web API.
See Tidal's documentation: https://developer.tidal.com/documentation/api/api-overview

# Using the Tidal API

## Installing

This package will be available on Maven's central repository when finished.

## Authorization

This package provides wrappes for the client credentials authorization flow.
See Tidal's documentation for retrieving the credentials: https://developer.tidal.com/documentation/dashboard/dashboard-client-credentials

The credentials must be re-fetched periodically.
This package ignores requests to authorization unless the credentials are less than an hour away from being invalidated, but this can be overridden with the `force` parameter.

## Getting started

Create an instance of the Tidal API and authorize with your own credentials as explained in the Authorization section.

```java
final String clientId = "<your_client_id>";
final String clientSecret = "<your_client_secret>";

final TidalApi api = new TidalApi();
api.authorize(clientId, clientSecret);
```

Once authorized, the client provides the available endpoints. Most requests must have a `countryCode` (e.g. "US"). List methods also provide optional skip/limit parameters that are disabled by default (all results are fetched).

All of the endpoints throw `QueryException`s in case of misuse or external errors.

### Examples

#### Tracks

```java
// Get track
final TidalTrack track = api.tracks.get("345485959", "US");

// List tracks by IDs
final TidalTrack[] tracks = api.tracks.list(new String[]{ "345485959" }, "US");

// List tracks by artist ID
final TidalTrack[] tracks = api.tracks.listByArtist("1566", "US");

// List 2 tracks by artist ID
final TidalTrack[] tracks = api.tracks.listByArtist("1566", "US", 2);

// List 2 tracks by artist ID, skip first 2 matches
final TidalTrack[] tracks = api.tracks.listByArtist("1566", "US", 2, 2);

// List tracks by ISRC code
final TidalTrack[] tracks = api.tracks.listByIsrc("USSM12209515", "US");

// List tracks similar to given ID
final TidalTrack[] tracks = api.tracks.listSimilar("345485959", "US");
```

# License

This project is licensed under the terms of the MIT license.
