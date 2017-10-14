# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.
class Details(models.Model):
    name = models.CharField(max_length=20)
    lastname = models.CharField(max_length=20)
    address = models.TextField(max_length=250)
