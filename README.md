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
This package ignores requests to authorization unless the credentials are less than an hour away from being invalidated, but this can be overridden with the 'force' parameter.

# License

This project is licensed under the terms of the MIT license.
