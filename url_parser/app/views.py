# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, HttpResponse
from django.core import serializers
from django.views import View
from app import models
from json import dumps
# Create your views here.

class Home(View):

    def get(self, request, *args, **kwargs):
        details = models.Details.objects.all()
        data = serializers.serialize('json', details)
        return HttpResponse(data, content_type='application/json')

    def post(self, request, *args, **kwargs):
        response_data = {}
        name = kwargs['name']
        lastname = kwargs['lastname']
        address = kwargs['address']
        models.Details.objects.create(name=name, 
                lastname=lastname,
                address=address)
        response_data['message'] = 'Created successfully'
        return HttpResponse(dumps(response_data), 
                content_type='application/json') 
