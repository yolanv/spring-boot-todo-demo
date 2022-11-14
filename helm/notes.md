## Workshop HELM
0. Start minikube
1. Create helm package
    - ``Helm create <chart name>``
2. Go into helm package
    - ``cd <chart name>``
3. Delete everything inside templates (except _helpers.tpl)
   - Clear values.yaml
   - Delete Test folder
4. Copy .yaml from kubernetes folder into templates
5. Install helm chart on minikube
   - ``helm install -f ./values.yaml <chart name> .``
   - ``kubectl get pod``
   - ``kubectl port-forward <pod name> 8080:8080``
6. Fill in replicas with a helm value
   - ``replicaCount: 1``
   - ``replicas: {{ .Values.replicaCount }}``
7. Upgrade current helm chart
   - ``helm upgrade -f ./values.yaml <chart name> .``
   - ``kubectl get pods``
8. Change replicaCount to 2
   - ``replicaCount: 2``
   - ``helm upgrade -f ./values.yaml <chart name> .``
   - ``kubectl get pods``
9. Show helm revisions and rollback to first version
   - ``helm history <chart name>``
   - ``helm rollback <chart name> 1``
   - ``kubectl get pods``
   - ``helm history <chart name>``
10. Add name too deployment and service (using value from _helpers.tpl)
    - ``name: {{ include "workshop.fullname" . }}``
    - Fill in the name in values.yaml
    - ``helm upgrade -f ./values.yaml <chart name> .``
    - ``kubectl get all``
    - *The dot is used to define the scope of the template parameter, the dot gives the full top level scope. As an example you could change the dot to '.Values' to see the effects. (Don't forget to change the name in _helpers.tpl)*
11. Change the labels (and selector labels) inside the deployment en service
    - ``{{- include "workshop.selectorLabels" . | nindent <num of indent> }}``
    - ``{{- include "workshop.labels" . | nindent <num of indent> }}``
    - ``helm uninstall <chart name>``
    - ``helm install <chart name>``
    - ``kubectl describe deploy``
12. Add multiple ports to the service
    - Using with to specify the scope
    - Using range to loop over multiple ports
    - Using an 'if' to check that the debugPort is filled in
    ```
    spec:
      {{- with .Values.service }}
      ports:
        {{- range .ports }}
        - port: {{.port}}
          targetPort: {{.targetPort}}
          protocol: {{.protocol}}
          name: {{.name}}
        {{- end }}
        {{- if .debugPort }}
        - port: {{.debugPort}}
          targetPort: {{.debugPort}}
          protocol: TCP
          name: debug
        {{- end }}
    type: {{ .type }}
    {{- end }}
    ```
    - ``helm upgrade -f ./values.yaml <chart name> .``
    - ``kubectl get svc``
13. Remove debug port from values file
    - ``helm upgrade -f ./values.yaml <chart name> .``
    - ``kubectl get svc``
14. Packaging your chart
    - Go outside the chart folder
    - ``helm package <chart name>``
