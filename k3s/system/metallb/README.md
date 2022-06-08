## Installation of Metal Lb
https://metallb.universe.tf/installation/

* kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/namespace.yaml
* kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/metallb.yaml
* kubectl apply -f config.yaml

We need to create a secret key for the speakers (the MetalLB pods) to encrypt speaker communications:
* kubectl create secret generic -n metallb-system memberlist --from-literal=secretkey="$(openssl rand -base64 128)"


# pfsense
https://www.danmanners.com/posts/2019-02-pfsense-bgp-kubernetes/
--
AS 64512
fib-update yes
listen on 192.168.10.1
router-id 192.168.10.1
network 192.168.0.1/24

neighbor 192.168.0.2 {
    remote-as 64513
    announce all
    descr "master0"
}

neighbor 192.168.10.100 {
    remote-as 64513
    announce all
    descr "node0"
}

neighbor 192.168.10.101 {
    remote-as 64513
    announce all
    descr "node1"
}

neighbor 192.168.10.102 {
    remote-as 64513
    announce all
    descr "node2"
}
--