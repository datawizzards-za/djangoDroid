# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.shortcuts import render, HttpResponse
from django.views import View
from app import models
from json import dumps
from rest_framework.response import Response
from rest_framework import serializers
from rest_framework import generics
# Create your views here.

class DetailsSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.Details
        fields = '__all__'

class GetDetails(generics.ListAPIView):
    serializer_class = DetailsSerializer

    def get_queryset(self):
        return models.Details.objects.all()

class Home(View):

    def get(self, request, *args, **kwargs):
        details = models.Details.objects.all()
        data = DetailsSerializer(details, many=True)
        results = {}
        results['users'] = data
        return Response(data)
    

class PostData(View):

	def get(self, request, *args, **kwargs):
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
				