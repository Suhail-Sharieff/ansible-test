#!/bin/bash

python3 -m venv venv
source ./venv/bin/activate
pip install ansible
pip install netaddr
echo Enjoy playbooks