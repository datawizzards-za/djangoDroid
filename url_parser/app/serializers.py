from rest_framework import serializers
from app.models import Details

class CreateContactSerializers(serializers.ModelSerializer):
	class Meta:
		model = Details
		fields = ['name', 'lastname', 'address']

	def create(self, validated_data):
		name = validated_data.get('name')
		lastname = validated_data.get('lastname')
		address = validated_data.get('address')

		dataset = Details.objects.create(name=name,
										 lastname=lastname,
										 address=address)
		return dataset

